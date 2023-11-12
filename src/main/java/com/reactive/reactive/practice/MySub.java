package com.reactive.reactive.practice;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class MySub implements Subscriber<Integer> {

    private Subscription s;

    @Override
    public void onSubscribe(Subscription s) {
        log.info("구독자 : 구독 완료");
        this.s = s;

        log.info("구독자 : 컨텐츠 1개 요청");
        this.s.request(11);
    }

    @Override
    public void onNext(Integer integer) {
        log.info("onNext() : " + integer);
    }

    @Override
    public void onError(Throwable t) {
        log.info("구독중 에러");
    }

    @Override
    public void onComplete() {
        log.info("구독 완료");
    }
}
