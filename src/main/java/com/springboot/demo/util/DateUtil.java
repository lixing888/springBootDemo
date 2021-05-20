package com.springboot.demo.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Copyright (C), 2018-2021
 * FileName: DateUtil
 * Author:   李兴
 * Date:     2021/5/20 15:29
 * Description: 18个Java8日期处理
 */
public class DateUtil {

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        System.out.println("year:" + year);
        System.out.println("month:" + month);
        System.out.println("day:" + day);

        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.of(1990, 9, 8);

        if (date1.equals(date2)) {
            System.out.println("时间相等");
        } else {
            System.out.println("时间不等");
        }

        MonthDay birthday = MonthDay.of(date2.getMonth(), date2.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(date1);

        if (currentMonthDay.equals(birthday)) {
            System.out.println("是你的生日");
        } else {
            System.out.println("你的生日还没有到");
        }

        LocalTime time = LocalTime.now();
        System.out.println("获取当前的时间,不含有日期:" + time);

        LocalTime newTime = time.plusHours(3);
        System.out.println("三个小时后的时间为:" + newTime);

        System.out.println("今天的日期为:" + today);
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("一周后的日期为:" + nextWeek);

        LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
        System.out.println("一年前的日期 : " + previousYear);

        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("一年后的日期:" + nextYear);

        Clock clock = Clock.systemUTC();
        System.out.println("Clock : " + clock.millis());

        // Returns time based on system clock zone
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("Clock : " + defaultClock.millis());

        LocalDate tomorrow = LocalDate.of(2018, 2, 6);
        if (tomorrow.isAfter(today)) {
            System.out.println("之后的日期:" + tomorrow);
        }

        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
        if (yesterday.isBefore(today)) {
            System.out.println("之前的日期:" + yesterday);
        }

        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime localtDateAndTime = LocalDateTime.now();
        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localtDateAndTime, america);
        System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork);

        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2019, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);

        if (today.isLeapYear()) {
            System.out.println("今年是闰年");
        } else {
            System.out.println("今年不是闰年");
        }

        LocalDate java8Release = LocalDate.of(2018, 12, 14);

        Period periodToNextJavaRelease = Period.between(today, java8Release);
        System.out.println("Months left between today and Java 8 release : "
                + periodToNextJavaRelease.getMonths());


        Instant timestamp = Instant.now();
        System.out.println("What is value of this instant 时间戳 " + timestamp.toEpochMilli());

        String dayAfterTommorrow = "20210520";
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow,
                DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(dayAfterTommorrow + "  格式化后的日期为:  " + formatted);


        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //日期转字符串
        String str = date.format(format1);
        System.out.println("日期转换为字符串:" + str);
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //字符串转日期
        LocalDate date3 = LocalDate.parse(str, format2);
        System.out.println("日期类型:" + date3);

    }
}
