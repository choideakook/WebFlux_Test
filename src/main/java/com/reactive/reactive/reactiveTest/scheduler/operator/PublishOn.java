package com.reactive.reactive.reactiveTest.scheduler.operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class PublishOn {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .doOnNext(data -> print("fromArray : " + data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> print("filter : " + data))
                .map(data -> data * 10)
                .doOnNext(data -> print("map : " + data))
                .subscribe(PublishOn::print);

        Thread.sleep(500L);
    }

    private static void print(String logs) {
        log.info(logs);
    }

    private static void print(Object logs) {
        log.info(logs.toString());
    }
}