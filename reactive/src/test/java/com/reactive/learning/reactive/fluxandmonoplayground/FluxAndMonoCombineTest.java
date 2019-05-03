package com.reactive.learning.reactive.fluxandmonoplayground;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * FluxAndMonoCombineTest
 */
public class FluxAndMonoCombineTest {

    @Test
    public void name() {
        Flux<String> flux = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("A", "B", "C");

        Flux<String> mergedFlux = Flux.merge(flux, flux2);

        StepVerifier.create(mergedFlux).expectSubscription().expectNext("A", "B", "C", "A", "B", "C").verifyComplete();
    }
}