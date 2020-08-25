package dev.donghyeon.modernjava;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;

public class ToXMLCollector implements Collector<Employee, StringBuffer, String> {

    final String xmlstr = "\n   <employee eid='%s'>\n\t" + "<name>%s</name>\n\t"
            + "<tech>%s</tech>\n\t<salary>%s</salary>\n   </employee>";

    public Supplier<StringBuffer> supplier() {
        return StringBuffer::new;
    }

    public BiConsumer<StringBuffer, Employee> accumulator() {
        return (sb, e) -> sb.append(String.format(xmlstr, e.empid, e.name, e.technology, e.salary));
    }

    public BinaryOperator<StringBuffer> combiner() {
        return (sb1, sb2) -> sb1.append(sb2.toString());
    }

    public Function<StringBuffer, String> finisher() {
        return sb -> String.format("<employees> %s \n</employees>", sb.toString());
    }

    public Set<Characteristics> characteristics() {
        return EnumSet.of(CONCURRENT);
    }


    public static void main(String[] args) {
        Set<Employee> emps = Set.of(new Employee("donghyeon", "D001", "Back-ENd Developer", 100000),
                new Employee("seohyun", "D002", "iOS Developer", 130000));
        String xmlstr = emps.parallelStream().collect(new ToXMLCollector());
        System.out.println(xmlstr);

        Collector.<Employee, StringBuffer, String>of(StringBuffer::new,
                (sb, e) -> sb.append(String.format(xmlstr, e.empid, e.name, e.technology)),
                (sb1, sb2) -> sb1.append(sb2.toString()),
                sb -> sb.insert(0, "<employees>").append("\n</employees>").toString(),
                Collections.emptySet());
    }
}