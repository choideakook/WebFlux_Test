package com.reactive.reactive.reactiveTest.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class BackpressureExample1 {
    public static void main(String[] args) {

        // Subscriber 가 처리 가능한 만큼 request 개수를 조절하는 Backpressure 예시
        Flux<Integer> flux = Flux.range(1, 5)
                // publisher 가 공급한 data 출력
                .doOnNext(BackpressureExample1::pubLog)
                // subscriber 가 요청한 data 수 출력
                .doOnRequest(BackpressureExample1::reqLog);

        // 요청 data 의 수를 조절하는 BaseSubscriber 생성
        flux.subscribe(new BaseSubscriber<Integer>() {

            // pub 으로 보낼 request
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                // 데이터 요청 갯수를 1로 설정
                request(1);
            }

            // sub 에서 data 를 받으면 실행되는 method
            @Override
            protected void hookOnNext(Integer value) {
                // 2초의 data 처리 후 다시 1개로 설정후 pub 으로 요청
                sleep(2000L);
                System.out.println("처리한 데이터 : " + value);
                request(1);
            }

            private void sleep(Long ms) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private static void pubLog(Object data) {
        System.out.println("공급한 데이터 : " + data);
    }

    private static void reqLog(Object data) {
        System.out.println("요청한 데이터 수 : " + data);
    }
}