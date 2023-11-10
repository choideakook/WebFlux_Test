package com.reactive.reactive.reactiveTest.sinks;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

public class CreateExample01 {
    public static void main(String[] args) {
        int tasks = 6;

        Flux
                .create((FluxSink<String> sinks) -> {
                    IntStream
                            .range(1, tasks)
                            .forEach(n -> sinks.next(doTask(n)));
                })
//                .subscribeOn(Schedulers.boundedElastic())
//                .doOnNext(n -> System.out.println("# create() : " + n))
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
