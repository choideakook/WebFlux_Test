package com.reactive.reactive.myApp;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MyRepository extends ReactiveCrudRepository<MyEntity, Long> {
}
