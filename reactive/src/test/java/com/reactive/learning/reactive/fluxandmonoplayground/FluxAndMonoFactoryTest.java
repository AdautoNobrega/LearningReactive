package com.reactive.learning.reactive.fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * FluxAndMonoFactoryTest
 */
public class FluxAndMonoFactoryTest {

    @Test
    public void fluxUsingIterable() {
        List<String> names = Arrays.asList("adam", "anna", "jack", "other");

        Flux<String> namesFlux = Flux.fromIterable(names).log();

        StepVerifier.create(namesFlux).expectNext("adam", "anna", "jack", "other").verifyComplete();
    }

    @Test
    public void fluxUsingArray() {
        String[] names = new String[] { "adam", "anna", "jack", "other" };

        Flux<String> namesFlux = Flux.fromArray(names).log();

        StepVerifier.create(namesFlux).expectNext("adam", "anna", "jack", "other").verifyComplete();
    }

    @Test
    public void fluxUsingStream() {

        Stream<String> names = Stream.of("adam", "anna", "jack", "other");

        Flux<String> namesFlux = Flux.fromStream(names).log();

        StepVerifier.create(namesFlux).expectNext("adam", "anna", "jack", "other").verifyComplete();

    }

    @Test
    public void fluxUsingRange() {
        Flux<Integer> iFlux = Flux.range(1, 5).log();

        StepVerifier.create(iFlux).expectNext(1, 2, 3, 4, 5).verifyComplete();
    }

    @Test
    public void monoUsingJustOrEmpty() {
        Mono<String> mono = Mono.justOrEmpty(null);

        StepVerifier.create(mono.log()).verifyComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> supplier = () -> "adam";

        Mono<String> monoSupplier = Mono.fromSupplier(supplier);
        StepVerifier.create(monoSupplier.log()).expectNext("adam").verifyComplete();
    }

}