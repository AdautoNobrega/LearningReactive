package com.reactive.learning.reactive.fluxandmonoplayground;

import static reactor.core.scheduler.Schedulers.parallel;

import java.util.Arrays;
import java.util.List;

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

    @Test
    public void transformUsingMapAndFilterTest() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("anna", "brazil", "cry", "d"))
                .filter(s -> s.length() > 4).map(String::toUpperCase).log();

        StepVerifier.create(namesFlux).expectNext("BRAZIL").verifyComplete();
    }

    @Test
    public void transformUsingFlatMapTest() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "C", "E", "F"))
                .flatMap(names -> Flux.fromIterable(convertToList(names)));

        StepVerifier.create(namesFlux).expectNextCount(12).verifyComplete();
    }

    private List<String> convertToList(String names) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(names, "nonValue");
    }

    @Test
    public void transformUsingFlatMapParallelTest() {
        Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "C", "E", "F")).window(2)
                .flatMap((n) -> n.map(this::convertToList).subscribeOn(parallel()).flatMap(Flux::fromIterable)).log();

        StepVerifier.create(namesFlux).expectNextCount(12).verifyComplete();
    }

    @Test
    public void tranformUsingFlatMapParallelMaintainOrderTest() {

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F")).window(2)
                .flatMapSequential((s) -> s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s)).log();

        StepVerifier.create(stringFlux).expectNextCount(12).verifyComplete();
    }

}