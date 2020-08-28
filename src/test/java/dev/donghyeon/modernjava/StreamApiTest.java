package dev.donghyeon.modernjava;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
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
        //reduce(초기값, p+str)
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
                .sorted(comparing(keyExtractor))
                .forEach(System.out::println);

        Stream.of("grapes", "milk", "pineapple", "water-melon")
                .sorted(comparing(String::length, Comparator.reverseOrder()))
                .forEach(System.out::println);
    }

    @Test
    public void comparator_no_boxing_cost_test() {
        // Natural order sorting by ignoring the sign.
        Stream.of(-10, 31, 16, -5, 2)
                .sorted(Comparator.comparingInt(i -> Math.abs(i)))
                .forEach(System.out::println);
    }

    @Test
    public void thenComparing_test() {
//        List<Employee> employees = Application.getEmployees();
//        employees.stream()
//                .sorted(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName))
//                .forEach(System.out::println);
    }

    @Test
    public void comparing_of_test() {

    }

    /**
     * toCollection 이나 toList는 기본으로
     * HashSet ArrayList를 사용하는데,
     * 다른 자료구조를 사용하고 싶으면 다음과같이 사용한다.
     */
    @Test
    public void want_another_set_test() {
        TreeSet<String> set = Stream.of("java", ".net", "python").map(String::toUpperCase)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Test
    public void toCollection_toMap_test() {
        Map<String, Integer> result = Stream.of("java", ".net", "python")
                .collect(Collectors.toMap(String::toUpperCase, String::length));

        //Output: {JAVA=4, .NET=4, PYTHON=6}
    }

    @Test
    public void toCollection_toMap_duplicatedKey_not_allowed_test() {
        Stream.of("java", ".net", "python", "jAvA")
                .collect(Collectors.toMap(String::toUpperCase, String::length, (value1, value2) -> value1))
                .forEach((k,v) -> System.out.println(k));

        System.out.println("----");
        
        Stream.of("java", ".net", "python", "jAvA")
                .collect(Collectors.toMap(String::toString, String::length, (value1, value2) -> value1))
                .forEach((k,y) -> System.out.println(k));
        
        
    }
    
    @Test
    public void String_joining_test() {
        String joiningResult = Stream.of("java", ".net", "python")
                .collect(joining(", ", "Joined String[ ", " ]"));

        System.out.println(joiningResult);

    }

    @Test
    public void grouping_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );

        Map<String, List<Trade>> collect = trades.stream()
                .collect(Collectors.groupingBy(Trade::getRegion));

        System.out.println(collect.toString());
    }

    @Test
    public void advanced_grouping_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );

        Map<String, Map<String, List<Trade>>> collect = trades.stream()
                .collect(Collectors.groupingBy(Trade::getRegion
                        , Collectors.groupingBy(Trade::getCurrency)));

        System.out.println(collect);
    }


    @Test
    public void advanced_grouping_choose_result_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );

        Map<String, Map<String, List<Trade>>> collect = trades.stream()
                .collect(Collectors.groupingBy(Trade::getRegion
                        , ConcurrentHashMap::new // 추가
                        , Collectors.groupingBy(Trade::getCurrency)));

        /**
         * groupingBy는 스레드 세이프하지 않으므로
         * groupingByConcurrency 메소드를 쓰면 내부적으로 ConcurrentHashMap을 사용하기 때문에 스레드 세이프함.
         */
    }

    @Test
    public void true_false_partitioning_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );

        Map<Boolean, List<Trade>> map2 = trades.stream()
                .collect(Collectors.partitioningBy(t -> "USD".equals(t.getCurrency())));
        System.out.println(map2);

        //{false=[T1002, T1005, T1006, T1007, T1008, T1009], true=[T1001, T1003, T1004]}
    }

    @Test
    public void simple_summarizingDouble_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );

        Map<String, DoubleSummaryStatistics> map = trades.stream()
                .collect(Collectors.groupingBy(Trade::getRegion,
                        Collectors.summarizingDouble(Trade::getNotional)));

        DoubleSummaryStatistics naData = map.get("NA");
        System.out.printf("No of deals: %d\nLargest deal: %f\nAverage deal cost: %f\nTotal traded amt: %f",
                naData.getCount(), naData.getMax(), naData.getAverage(), naData.getSum());

    }

    @Test
    public void simple_collectingAndThen_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );
        // set -> unmodifableSet 으로 변환
        Set<Trade> set = trades.stream().collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
    }

    @Test
    public void collectingAndThen의_좋은예제_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );

        Map<String, Optional<Trade>> map1 = trades.stream()   // Solution-1
                .collect(groupingBy(Trade::getRegion, maxBy(comparing(Trade::getNotional))));


        Map<String, Trade> map2 = trades.stream()             // Solution-2
                .collect(groupingBy(Trade::getRegion,
                        collectingAndThen(maxBy(comparing(Trade::getNotional)), Optional::get)));
        //maxBy가 Optional을 리턴하기 때문에, collectingAndThen으로 Optional.get()을 호출해서 풀어주는 모습.
    }
    /*
        collectionAndThen : A -> B
        mapping : A <- B
     */
    @Test
    public void mapping_test() {
        List<Trade> trades = Arrays.asList(
                new Trade("T1001", "John", 540000, "USD", "NA"),
                new Trade("T1002", "Mark", 10000, "SGD", "APAC"),
                new Trade("T1003", "David", 120000, "USD", "NA"),
                new Trade("T1004", "Peter", 4000, "USD", "NA"),
                new Trade("T1005", "Mark", 300000, "SGD", "APAC"),
                new Trade("T1006", "Mark", 25000, "CAD", "NA"),
                new Trade("T1007", "Lizza", 285000, "EUR", "EMEA"),
                new Trade("T1008", "Maria", 89000, "JPY", "EMEA"),
                new Trade("T1009", "Sanit", 1000000, "INR", "APAC")
        );

        Map<String, Set<String>> map = trades.stream()
                .collect(groupingBy(Trade::getRegion, mapping(Trade::getTradeId, toSet())));
        System.out.println(map);
    }



}
