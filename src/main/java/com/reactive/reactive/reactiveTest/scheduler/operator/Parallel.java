package com.reactive.reactive.reactiveTest.scheduler.operator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Parallel {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                .parallel()
                .runOn(Schedulers.parallel())
                .subscribe(Parallel::print);

        Thread.sleep(100L);
    }

    private static void print(Object logs) {
        log.info("onNext(): {}", logs.toString());
    }
}