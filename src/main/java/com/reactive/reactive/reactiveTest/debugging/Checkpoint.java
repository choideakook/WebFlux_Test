package com.reactive.reactive.reactiveTest.debugging;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

public class Checkpoint {
    public static void main(String[] args) {
        Start2 s = new Start2();
        s.no2();
    }
}

@Slf4j
class Start2 {
    public void no1() {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x / y)
                .checkpoint()
                .map(num -> num + 2)
                .checkpoint()
                .subscribe(Start2::onNext);
    }


    public void no2() {
        Flux.just(2, 4, 6, 8)
                .zipWith(Flux.just(1, 2, 3, 0), (x, y) -> x / y)
                .checkpoint("zipWith.checkpoint", true)
                .map(num -> num + 2)
                .checkpoint("map.checkpoint", true)
                .subscribe(Start2::onNext);
    }

    private static void onNext(Object logs) {
        log.info("onNext : {}", logs.toString());
    }
}