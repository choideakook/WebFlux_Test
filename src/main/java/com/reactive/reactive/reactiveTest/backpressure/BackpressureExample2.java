package com.reactive.reactive.reactiveTest.backpressure;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class BackpressureExample2 {

    public static int count = 0;

    public static void main(String[] args) {

        Flux<Integer> flux = Flux.range(1, 5)
                .doOnNext(BackpressureExample2::pubLog)
                .doOnRequest(BackpressureExample2::reqLog);

        flux.subscribe(new BaseSubscriber<Integer>() {

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(2);
            }

            @Override
            protected void hookOnNext(Integer value) {
                count++;
                System.out.println("처리한 데이터 : " + value);

                if (count == 2) {
                    sleep(2000L);
                    request(2);
                    count = 0;
                }
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