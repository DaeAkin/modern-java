package dev.donghyeon.modernjava;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;

public class StreamApiTest {

    private List<String> testList = Arrays.asList("aaa","bbb","ccc");

    @Test
    public void allMatch_test() {
        boolean result = testList.stream()
                .allMatch(s -> s instanceof String);

        assertThat(result).isTrue();
    }

    @Test
    public void anyMatch_test() {
        boolean result = testList.stream()
                .anyMatch(s -> s.contains("aaa"));

        assertThat(result).isTrue();
    }

    @Test
    public void filter_test() {
        Stream<String> result = testList.stream()
                .filter(s -> s.contains("bbb"));

        assertThat(result.count()).isEqualTo(1);
    }

    @Test
    public void map_test() {
        Stream<Integer> integerStream = testList.stream()
                .map(String::length);
    }

    @Test
    public void flatMap_test() {
        testList.stream()
                .flatMap(s ->{
                    return "aa";
                })
    }



}
