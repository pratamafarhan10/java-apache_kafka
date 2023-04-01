package com.example.kafkaconsumer.reactive.product;

import com.example.kafkaconsumer.repositories.product.entity.Product;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ProductSubscriber implements Subscriber<Product> {
    private Subscription subscription;
    private int batchCount;
    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(2);
    }

    @Override
    public void onNext(Product product) {
        batchCount++;
        if (batchCount == 2) {
            batchCount = 0;
            subscription.request(2);
        }
        System.out.println("Product: " + product);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Print done");
    }
}
