package com.reactive.reactive.myApp;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MyRepository extends ReactiveCrudRepository<MyEntity, Long> {
    Mono<MyEntity> findByName(String name);
}
