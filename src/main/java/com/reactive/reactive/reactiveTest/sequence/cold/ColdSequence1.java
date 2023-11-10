package com.reactive.reactive.reactiveTest.sequence.cold;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class ColdSequence1 {
    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"))
                .map(String::toLowerCase);

        coldFlux.subscribe(country -> System.out.println("구독 1 : " + country));
        System.out.println("----------------------");
        coldFlux.subscribe(country -> System.out.println("구독 2 : " + country));
    }
}
