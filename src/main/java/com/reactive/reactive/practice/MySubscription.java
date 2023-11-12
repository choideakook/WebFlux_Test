package com.reactive.reactive.practice;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Iterator;

@Slf4j
public class MySubscription implements Subscription {

    private Subscriber s;
    private Iterator<Integer> it;

    public MySubscription(Subscriber s, Iterable<Integer> it) {
        this.s = s;
        this.it = it.iterator();
    }

    @Override
    public void request(long n) {
        while (n > 0) {
            if (it.hasNext()) {
                s.onNext(it.next());
                n--;
            }else {
                s.onComplete();
                break;
            }
        }
    }

    @Override
    public void cancel() {

    }
}
