package com.reactive.reactive.practice;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.Arrays;

@Slf4j
public class MyPub implements Publisher<Integer> {

    Iterable<Integer> its = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @Override
    public void subscribe(Subscriber<? super Integer> s) {
        log.info("구독자 : 구독 신청");
        log.info("신문사 : 구독 신청 확인");

        MySubscription subscription = new MySubscription(s, its);
        log.info("신문사 : 구독 정보 생성 완료");

        s.onSubscribe(subscription);
    }
}
