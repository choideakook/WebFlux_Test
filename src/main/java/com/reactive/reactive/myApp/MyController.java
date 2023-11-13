package com.reactive.reactive.myApp;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final MyService service;

    @PostMapping("/{name}")
    public Mono<MyEntity> save(
            @PathVariable String name
    ) {
        return service.save(name);
    }


    @GetMapping("/")
    public Flux<MyEntity> findAll() {
        return service.findAll();
    }
}
