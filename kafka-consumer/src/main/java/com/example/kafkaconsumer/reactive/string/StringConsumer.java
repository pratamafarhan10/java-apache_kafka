package com.example.kafkaconsumer.reactive.string;

import java.util.function.Consumer;

public class StringConsumer implements Consumer<String> {
    @Override
    public void accept(String s) {
        System.out.println("Data: " + s);
    }

    @Override
    public Consumer<String> andThen(Consumer<? super String> after) {
        return Consumer.super.andThen(after);
    }
}
