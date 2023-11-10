package com.reactive.reactive.reactiveTest.sinks;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class SinksExample01 {
    public static void main(String[] args) {
        int task = 6;

        Sinks.Many<Object> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> fluxView = unicastSink.asFlux();

        IntStream
                .range(1, task)
                .forEach(n -> {
                    try {
                        new Thread(() -> {
                            unicastSink.emitNext(doTask(n), Sinks.EmitFailureHandler.FAIL_FAST);
                            System.out.println("# emitted : " + n);
                        }).start();
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                    }
                });

        fluxView
//                .publishOn(Schedulers.parallel())
//                .map(result -> result + " success!")
//                .doOnNext(n -> System.out.println("# map() : " + n))
//                .publishOn(Schedulers.parallel())
                .subscribe(data -> System.out.println("# onNext : " + data));
    }

    private static String doTask(int taskNumber) {
        return "task " + taskNumber + " result";
    }
}
