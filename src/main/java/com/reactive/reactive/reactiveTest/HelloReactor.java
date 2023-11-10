package com.reactive.reactive.reactiveTest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class HelloReactor {
    public static void main(String[] args) {
        List<String> list = Arrays.asList(new String[]{"Hello", "Reactor"});
        constructTest(list);
    }

    private static void hello() {
        Flux<String> sequence = Flux.just("Hello", "Reactor");
        sequence
                .map(data -> data.toLowerCase())
                .subscribe(data -> log.info(data));
    }

    private static void staticTest(List<String> list) {
        list.stream()
                .forEach(System.out::println);
    }

    private static void constructTest(List<String> list) {
        list.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}