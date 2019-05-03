package com.reactive.learning.reactive.fluxandmonoplayground;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {

        Flux<String> sFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                /* .concatWith(Flux.error(new RuntimeException("Exception Occurred"))) */
                .concatWith(Flux.just("After Error")).log();

        sFlux.subscribe(System.out::println, (e) -> System.err.println("Exception is " + e),
                () -> System.out.println("Completed"));
    }

    @Test
    public void fluxElementsWithoutOnErrorTest() {
        Flux<String> sFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring").log();

        StepVerifier.create(sFlux).expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxElementsWithOnErrorTest() {
        Flux<String> sFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred"))).log();

        StepVerifier.create(sFlux).expectNext("Spring").expectNext("Spring Boot").expectNext("Reactive Spring")
                .expectError(RuntimeException.class).verify();
    }

    @Test
    public void fluxElementsGroupWithOnErrorTest() {
        Flux<String> sFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred"))).log();

        StepVerifier.create(sFlux).expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectErrorMessage("Exception Occurred").verify();
    }

    @Test
    public void fluxElementsCountWithOnErrorTest() {
        Flux<String> sFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred"))).log();

        StepVerifier.create(sFlux).expectNextCount(3).expectError(RuntimeException.class).verify();
    }

    @Test
    public void monoElementTest() {
        Mono<String> sMono = Mono.just("Spring Boot Reactive").log();

        StepVerifier.create(sMono).expectNext("Spring Boot Reactive").verifyComplete();
    }

    @Test
    public void monoElementErrorTest() {
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred"))).expectError(RuntimeException.class)
                .verify();
    }

}