package com.reactive.learning.reactive.fluxandmonoplayground;

import java.util.Arrays;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * FluxAndMonoFilterTest
 */
public class FluxAndMonoFilterTest {

    @Test
    public void fluxFilterTest() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("adam", "anna", "jack", "jenny"))
                .filter(s -> s.startsWith("a")).log();

        StepVerifier.create(namesFlux).expectNext("adam", "anna").verifyComplete();
    }

    @Test
    public void fluxFilterLengthTest() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("adam", "anna", "jack", "jenny"))
                .filter(s -> s.length() > 4).log();

        StepVerifier.create(namesFlux).expectNext("jenny").verifyComplete();
    }
}