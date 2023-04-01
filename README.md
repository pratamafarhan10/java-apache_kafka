# Java Spring Boot Apache Kafka Tutorial

Introduction to configure microservices for your spring boot projects.

Outline:
1. Configuring kafka in local machine (Windows only)
2. Prepare your spring boot projects
2. Configure producer service
3. Configure consumer service

## Configuring kafka in local machine

- Download the **binary file** for apache kafka from [Apache Kafka Download Page](https://kafka.apache.org/downloads "Apache kafka download link")
- Follow the steps from this [Geeks for Geeks tutorial for windows Page](https://www.geeksforgeeks.org/how-to-install-and-run-apache-kafka-on-windows/ "Geeks for Geesk tutorial apache kafka")
- Run kafka from your local machine
 
## Prepare your spring boot projects

Create 2 spring boot project and make sure you install these libraries:
- Spring for Apache Kafka
- Jackson-core

After that don't forget to set the Kafka bootstrap server url in our both project application.properties
```
spring.kafka.bootstrap-server={your Kafka bootstrap server}
```
 
## Configure producer service

After you create two spring boot project, you have to decide which one is the producer or the consumer one. For the producer service you have to create these files.

### KafkaTopicConfig
This configuration class aims to create a specific topic on which we will publish messages.
```java
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productTopic(){
        return TopicBuilder.name("ecommerce-product").build();
    }
}
```

### KafkaProducerConfig
This configuration class aims to create the configuration for our kafka producer so that we can send messages to a specific topic. As you can see, we have a key value pairs which mean that we will send data with a key of "String" and a value with a type of "String"
```java
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;

    public Map<String, Object> producerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
```

### ProductController
Next we create a controller class that will receive a request. Notice that we have to convert the request into a JSON string so that we can send the message to our Kafka. Then we have to call our kafkaTemplate that we already created in our configuration class to send our message with a specific topic.
```java
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/create")
    public boolean createProduct(@RequestBody ProductRequest request) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            String reqJson = mapper.writeValueAsString(request);
            kafkaTemplate.send("ecommerce-product", reqJson);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
```

### ProductRequest
This is a basically a POJO class that will be our request body.
```java
@Data
public class ProductRequest {
    private String name;
    private String description;
    private int price;
}
```

## Configure consumer service

### KafkaConsumerConfig
This is our configuration class for our consumer. Basically we have similar configuration with our producer project. But we have to change the consumer config props to ConsumerConfig. Next we have to set our consumer factory.
```java
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;

    public Map<String, Object> consumerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> factory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
```

### KafkaListenerConfig
In this class, we have to define our listener method that will listen to a specific topic. on @KafkaListener annotation, we have to define the topics and the groupId. The purpose of groupId is to group different listener with the same topic so that we can balance the load in different partition with the same topic. The data will be received in the parameter as a String, then we have to deserialize it with ObjectMapper from jackson-core library.
```java
@Component
public class KafkaListenerConfig {

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @KafkaListener(
            topics = "ecommerce-product",
            groupId = "ecommerce-product-1"
    )
    void listener(String data){
        try{
            System.out.println("\nData received: " + data);
            ObjectMapper mapper = new ObjectMapper();
            JsonParser parser = mapper.createParser(data);
            Product product =  parser.readValueAs(Product.class);

            this.productRepositoryService.saveProduct(product);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
```
