package com.isalnikov.reactiveservice;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

    @Autowired
    Function<Flux<String>, Flux<String>> uppercase;

    public Mono<ServerResponse> hello(ServerRequest request) {

        Mono<String> mono = uppercase.apply(Flux.just("Hello, Spring!")).next();

        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                //.body(BodyInserters.fromObject( "Hello, Spring!"));
                .body(BodyInserters.fromPublisher(mono, String.class));
    }
}
