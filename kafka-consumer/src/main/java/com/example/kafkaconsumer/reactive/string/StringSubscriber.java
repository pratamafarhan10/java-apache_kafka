package com.example.kafkaconsumer.reactive.string;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Data
public class StringSubscriber implements Subscriber<String> {
    private int dataCount;
    private Subscription subscription;
    private int batchCount;
    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(2);
    }

    @Override
    public void onNext(String s) {
        batchCount++;
        if (batchCount == 2){
            batchCount = 0;
            this.subscription.request(2);
        }
        System.out.println("Data: " + s.toUpperCase());
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("done");
    }
}
