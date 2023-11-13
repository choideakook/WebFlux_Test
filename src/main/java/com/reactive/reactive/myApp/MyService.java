package com.reactive.reactive.myApp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MyService {

    private final MyRepository repository;

    public Mono<MyEntity> save(String name) {
        MyEntity myEntity = MyEntity.create(name);
        return repository.save(myEntity);
    }

    
    public Flux<MyEntity> findAll() {
        return repository.findAll();
    }
}
