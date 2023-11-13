package com.reactive.reactive.myApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class MyConfig {

    @Bean
    public Sinks.Many<MyEntity> myEntityMany() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
