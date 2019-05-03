package com.reactive.learning.reactive.fluxandmonoplayground;

import java.util.Arrays;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * FluxAndMonoTransformTest
 */
public class FluxAndMonoTransformTest {

    @Test
    public void transformUsingMapTest() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("a", "b", "c", "d")).map(String::toUpperCase).log();

        StepVerifier.create(namesFlux).expectNext("A", "B", "C", "D").verifyComplete();
    }

    @Test
    public void transformToIntegerUsingMapTest() {
        Flux<Integer> namesFlux = Flux.fromIterable(Arrays.asList("anna", "brazil", "cry", "d")).map(String::length)
                .log();

        StepVerifier.create(namesFlux).expectNext(4, 6, 3, 1).verifyComplete();
    }

    @Test
    public void transformToIntegerUsingMapRepeatTest() {
        Flux<Integer> namesFlux = Flux.fromIterable(Arrays.asList("anna", "brazil", "cry", "d")).map(String::length)
                .repeat(1).log();

        StepVerifier.create(namesFlux).expectNext(4, 6, 3, 1, 4, 6, 3, 1).verifyComplete();
    }

}