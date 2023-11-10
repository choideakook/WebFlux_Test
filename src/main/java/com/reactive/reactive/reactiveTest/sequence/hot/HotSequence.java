package com.reactive.reactive.reactiveTest.sequence.hot;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class HotSequence {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> concertflux = Flux
                .fromStream(Stream.of("Singer A", "Singer B", "Singer C", "Singer D", "Singer E"))
                // delay 를 사용해 1초에 한번씩 data 를 publish 하도록 설정
                .delayElements(Duration.ofSeconds(1))
                // 하나의 타임라인으로 다수의 subscriber 가 공유하도록 설정
                .share();

        concertflux.subscribe(singer -> System.out.println("구독 1 : " + singer));

        Thread.sleep(2500);

        concertflux.subscribe(singer -> System.out.println("구독 2 : " + singer));

        // 요청이 종료되지 않고 모든 타임라인이 끝날 때 까지 지연시킴
        Thread.sleep(3000);
    }
}
