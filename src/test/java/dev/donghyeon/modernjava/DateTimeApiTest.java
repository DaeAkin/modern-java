package dev.donghyeon.modernjava;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoField;

public class DateTimeApiTest {

    @Test
    public void localDate_test() {
        LocalDate.of(2015, 03, 18);      // When individual values know
        LocalDate.parse("2015-03-18");  //  Creating from date string

        LocalDate.now();                // To get the current date.
        LocalDate.now(ZoneId.of("America/Chicago"));
    }

    @Test
    public void localTime_test() {
        LocalTime date = LocalTime.of(4, 30, 15);
        LocalTime.parse("04:30:15.12345");

        LocalTime.now();
        LocalTime.now(ZoneId.of("America/Chicago"));

        System.out.println(date.getMinute());
        System.out.println(date.getNano());
        System.out.println(date.get(ChronoField.HOUR_OF_DAY));
    }

    @Test
    public void localDateTime_test() {
        LocalDateTime date = LocalDateTime.now();

        LocalDateTime.parse("2007-12-03T10:15:30");

        System.out.println(date.getDayOfWeek());
        System.out.println(date.toLocalDate());
        System.out.println(date.toLocalTime());
    }

    /*
      instant는 Date 하고 비슷한 것 같다.
     */
    @Test
    public void instant_test() {
         Instant.now();
         Instant.now().getEpochSecond();

         Instant.parse("1969-01-01T00:00:00.00Z").getEpochSecond(); // --> -31,536,000
         Instant.parse("1971-01-01T00:00:00.00Z").getEpochSecond(); // --> 31,536,000
    }

    @Test
    public void duration_test() {
        LocalDateTime d1 = LocalDateTime.parse("2014-12-03T10:15:30");
        LocalDateTime d2 = LocalDateTime.parse("2016-03-05T23:15:00");
        Duration duration = Duration.between(d1, d2);

        duration.toHours();
        duration.toDays();

        Duration.between(d1.toLocalTime(), d2).toHours();  // 12
        Duration.between(d1, d2.toLocalTime()).toHours();  // DateTimeException
        Duration.between(d1.toLocalDate(), d2.toLocalDate());  // DateTimeException
    }

}
