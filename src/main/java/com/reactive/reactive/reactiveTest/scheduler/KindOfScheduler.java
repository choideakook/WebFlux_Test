package com.reactive.reactive.reactiveTest.scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class KindOfScheduler {
    public static void main(String[] args) throws InterruptedException {
        Start start = new Start();
//        start.immediate();
        start.newBoundedElastic();
    }
}

@Slf4j
class Start {
    public void immediate() throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .publishOn(reactor.core.scheduler.Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> print("filter : " + data))
                .publishOn(reactor.core.scheduler.Schedulers.immediate())
                .map(data -> data * 10)
                .doOnNext(data -> print("map : " + data))
                .subscribe(Start::print);

        Thread.sleep(500L);
    }

    public void single() throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 3, 5, 7})
                .publishOn(reactor.core.scheduler.Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> print("filter : " + data))
                .publishOn(reactor.core.scheduler.Schedulers.single())
                .map(data -> data * 10)
                .doOnNext(data -> print("map : " + data))
                .subscribe(Start::print);

        Thread.sleep(500L);
    }

    public void newBoundedElastic() {
        Scheduler scheduler = Schedulers.newBoundedElastic(2, 2, "my-Thread");
        Mono<Integer> mono = Mono
                .just(1)
                .subscribeOn(scheduler);

        for (int i = 1; i < 7; i++)
            subscribe(mono, getMs(i), i);
    }

    private Long getMs(int i) {
        if (i > 2)
            return 0L;
        else
            return 3000L;
    }

    private void subscribe(Mono<Integer> mono, Long ms, int number) {
        mono.subscribe(data -> {
            log.info("subscribe " + number + " doing : {}", data);
            sleep(ms);
            log.info("subscribe " + number + " done : {}", data);
        });
    }

    private void sleep(Long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void print(String logs) {
        log.info(logs);
    }

    private static void print(Object logs) {
        log.info("sub : {}", logs.toString());
    }
}