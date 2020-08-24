package dev.donghyeon.modernjava;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static java.util.stream.Collectors.toList;
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

    }

    @Test
    public void limit_skip_test() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        stream.filter(i -> i%2 == 0).limit(2).forEach(System.out::println);

        System.out.println("-----");

        stream = Stream.of(1, 2, 3, 4, 5);
        stream.filter(i -> i%2 == 0).skip(1).forEach(System.out::println);
    }

    @Test
    public void pick_foreach_test() {
        Stream<Integer> stream = Stream.of(1, 2, -3, 4, 5);
        stream.filter(i -> i%2 == 0).peek(System.out::print).toArray();

        stream = Stream.of(1, 2, -3, 4, 5);
        stream.filter(i -> i%2 == 0).forEach(System.out::println);
    }

    @Test
    public void reduceThreeArgs_test() {
        List<String> words = Arrays
                .asList("This is stream reduction example learn well".split(" "));

        int result = words.stream().reduce(0, (p, str) -> {
            System.out.println("BiFunc: " + p + "  " + str);
            return p + str.length();
        }, (i, j) -> {
            System.out.println("BiOpr: " + i + "  " + j);
            return i + j;
        });
    }

    @Test
    public void comparator_test() {
        // Sorting words based on word lengths
        Function<String, Integer> keyExtractor = str -> str.length();
        Stream.of("grapes", "milk", "pineapple", "water-melon")
                .sorted(Comparator.comparing(keyExtractor))
                .forEach(System.out::println);
    }




}
