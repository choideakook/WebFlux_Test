package com.reactive.reactive.myApp;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final MyService service;
    private final Sinks.Many<MyEntity> sink;


    @PostMapping("/{name}")
    public Mono<MyEntity> save(
            @PathVariable String name
    ) {
        return service.save(name)
                .doOnNext(m -> {
                    sink.tryEmitNext(m);
                });
    }

    // 생략됨 : produces = MediaType.TEXT_EVENT_STREAM_VALUE
    @GetMapping("/sse/all")
    public Flux<ServerSentEvent<MyEntity>> allBySSE() {
        return sink.asFlux().map(m ->
                        ServerSentEvent
                                .builder(m)
                                .build()
                )
                .doOnCancel(() -> {
                    sink.asFlux().blockLast();
                });
    }


    @GetMapping("/")
    public Flux<MyEntity> findAll() {
        return service.findAll().log();
    }

    @GetMapping("/flux")
    public Flux<Integer> flux() {
        return Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofSeconds(1)).log();
    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> stream() {
        return Flux.just(11, 12, 13, 14, 15).delayElements(Duration.ofSeconds(1)).log();
    }

    @GetMapping("/id/{id}")
    public Mono<MyEntity> findById(
            @PathVariable Long id
    ) {
        return service.findById(id).log();
    }

    @GetMapping("/name/{name}")
    public Mono<MyEntity> byName(
            @PathVariable String name
    ) {
        return service.findByName(name).log();
    }
}
