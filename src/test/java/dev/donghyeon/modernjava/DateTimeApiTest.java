package dev.donghyeon.modernjava;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.next;

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

    @Test
    public void period_test() {
        LocalDate date1 = LocalDate.parse("2010-01-15");
        LocalDate date2 = LocalDate.parse("2011-03-18");

        Period period = Period.between(date1, date2);
        period.getYears();     // 1
        period.getMonths();    // 2
        period.getDays();      // 3

        System.out.println(period);

        System.out.println(date1.until(date2, ChronoUnit.DAYS));
    }

    @Test
    public void temporalAdjuster_test() {
        LocalDate date = LocalDate.parse("2014-03-18");
        TemporalAdjuster adjuster = TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY);
        System.out.println(date.with(adjuster));
    }

    @Test
    public void temporalAdjuster_with_lambda_test() {
        TemporalAdjuster nextWorkingday = temporal -> {
            LocalDate date = (LocalDate) temporal;
            DayOfWeek day = date.getDayOfWeek();
            if (DayOfWeek.FRIDAY.equals(day) || DayOfWeek.SATURDAY.equals(day)) {
                return date.with(next(DayOfWeek.MONDAY));
            } else {
                return date.plusDays(1);
            }
        };

        System.out.println(LocalDate.now().with(nextWorkingday));
    }

    /*
        예전 DateFormat 스레드세이프 하지 않기 때문에,
        스레드 세이프한 새로운 java.time.format 가 나타났다.
     */

    @Test
    public void dateTimeFormatter_test() {
//        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//        LocalDate date = (LocalDate)f1.parse("18-Mar-2014");
//        String format = f1.format(LocalDate.of(2014, 3, 18));//  18-Mar-2014
//        System.out.println(format);

        //For localization
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.FRENCH);
        String format1 = f2.format(LocalDate.of(2014, 3, 18));//  18-mars-2014
        System.out.println(format1);
    }

    //time zone
    /*
        클래스
        ZoneID : 지역과 시티를 합쳐 ID를 만듬. Asia/Korean
        ZoneOffset : Represents timezone with an offset from Greenwich/UTC, such as +05:30.
        ZonedDateTime : IOS-8601 캘린더 시스템에 2007-12-03T10:15:30+01:00 Europe/Paris 와 같이 날짜와 시간을 나타낸다
        OffsetDateTime : A date-time with an offset from UTC/Greenwich in the ISO-8601 calendar system, such as 2007-12-03T10:15:30+01:00.
        OffsetTime : A time with an offset from UTC/Greenwich in the ISO-8601 calendar system, such as 10:15:30+01:00.
        ZoneRulesProvider : Provides time zone rules.

        It is not recommended to use ZoneOffset as they don’t contain daylight saving details if a country or city supporting it.
        (ZoneOffset은 국가 나 도시에서 지원하는 경우 일광 절약 세부 정보가 포함되어 있지 않으므로 사용하지 않는 것이 좋습니다.)
     */

    @Test
    public void zonedDateTime_test() {
        ZoneId zone = ZoneId.of("Asia/Kolkata");

        LocalDateTime dateTime = LocalDateTime.parse("2014-12-03T10:15:30");
        ZonedDateTime z11 = dateTime.atZone(zone);
        ZonedDateTime z12 = ZonedDateTime.of(dateTime, zone);
        System.out.println(z11.toString());

        LocalDate date = LocalDate.of(2014, 3, 18);
        ZonedDateTime z21 = date.atStartOfDay(zone);
        ZonedDateTime z22 = ZonedDateTime.of(date, LocalTime.now(), zone);

        Instant instant = Instant.now();
        ZonedDateTime z31 = instant.atZone(zone);
        ZonedDateTime z32 = ZonedDateTime.ofInstant(instant, zone);
    }

    @Test
    public void offsetDateTime_test() {
        /*
            As we saw time zones are also represented by an offset value from UTC,
            OffsetDateTime represents an object with date/time information and an offset,
            for example, 2014-12-03T11:30-06:00. Instant, OffsetDateTime and ZonedDateTime are very much looks similar
            but there are key differences exists. Instant represents a point in time in UTC on a continuous time line,
            OffsetDateTime maintains time zone with an offset compared to UTC and ZonedDateTime contains time zone information along with Day-Light-saving rules.
            It is always better to use ZonedDateTime or Instant for simple usages. As like other temporal instances, this also has standard method patterns to create its instances.
         */
        ZoneOffset offset = ZoneOffset.of("-2");

        OffsetDateTime.now(offset);
        OffsetDateTime.of(LocalDateTime.now(), offset);
        OffsetDateTime.of(LocalDate.now(), LocalTime.now(), offset);
        OffsetDateTime.ofInstant(Instant.now(), offset);
    }
}
