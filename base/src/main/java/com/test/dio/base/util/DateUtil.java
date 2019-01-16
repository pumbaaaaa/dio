package com.test.dio.base.util;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

public class DateUtil {

    public static Date parseStrWithPattern(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormat.forPattern(pattern)).toDate();
    }
}
