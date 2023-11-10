package com.reactive.reactive.reactiveTest.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Context {
    public static void main(String[] args) {
        Start start = new Start();
        start.http();
    }
}

@Slf4j
class Start {
    private String key = "msg";
    private String HEADER_TOKEN = "authToken";

    public void http() {
        Mono<String> mono = postBook(
                Mono.just(
                        new Book("ab-11-11-11",
                                "Reactor"))
        )
                .contextWrite(context -> context.put(HEADER_TOKEN, "adsfadsf"));

        mono.subscribe(Start::onNext);
    }

    private Mono<String> postBook(Mono<Book> book) {
        return Mono.zip(book, Mono.deferContextual(ctx -> Mono.just(ctx.get(HEADER_TOKEN))))
                .flatMap(tuple -> Mono.just(tuple))
                .flatMap(tuple -> {
                    String response = "POST the book(" +
                            tuple.getT1().getIsbn() + ", " +
                            tuple.getT1().getName() +
                            "with token : " +
                            tuple.getT2() + ")";
                    return Mono.just(response);
                });
    }


    public void context() {
        Mono<String> mono = Mono.deferContextual(ctx ->
                        Mono
                                .just("Hello" + " " + ctx.get(key))
                                .doOnNext(Start::doOnNext)
                )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                .transformDeferredContextual((mono2, ctx) ->
                        mono2.map(data ->
                                data + " " + ctx.get(key)))
                .contextWrite(context -> context.put(key, "Reactor"));

        mono.subscribe(Start::onNext);
    }

    private static void doOnNext(String logs) {
        log.info("doOnNext : {}", logs);
    }

    private static void onNext(String logs) {
        log.info("onNext : {}", logs);
    }
}

class Book {
    private String isbn;
    private String name;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Book(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }
}
