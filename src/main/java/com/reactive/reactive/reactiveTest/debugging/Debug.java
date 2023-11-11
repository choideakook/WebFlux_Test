package com.reactive.reactive.reactiveTest.debugging;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

import java.util.HashMap;
import java.util.Map;

public class Debug {
    public static void main(String[] args) {
        Hooks.onOperatorDebug();

        Start start = new Start();
        start.no2();
    }
}

@Slf4j
class Start {

    private Map<String, String> fruits = new HashMap<>();

    {
        fruits.put("banana", "바나나");
        fruits.put("apple", "사과");
        fruits.put("pear", "배");
        fruits.put("grape", "포도");
    }

    public void no2() {
        Flux.fromArray(new String[]{"BANANAS", "APPLES", "PEARS", "MELONS"})
                .map(String::toLowerCase)
                .map(fruit -> fruit.substring(0, fruit.length() - 1))
                .map(fruits::get)
                .subscribe(Start::onNext);
    }

    public void no1() {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x / y)
                .subscribe(Start::onNext);
    }

    private static void onNext(Object logs) {
        log.info("onNext : {}", logs.toString());
    }

    private void onError(String logs) {
        throw new RuntimeException(logs);
    }
}