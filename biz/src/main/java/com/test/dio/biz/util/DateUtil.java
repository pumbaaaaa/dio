package com.test.dio.biz.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

public class DateUtil {

    public static Date parseStrWithPattern(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormat.forPattern(pattern)).toDate();
    }

    public static String getDateFromMilliseconds(String checkTime) {
        DateTime dateTime = new DateTime(Long.valueOf(checkTime.concat("000")));
        return dateTime.toString("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static Date getDateFromReplyTime(String replyTime, String pattern) {
        return LocalDateTime.parse(replyTime, DateTimeFormat.forPattern(pattern)).toDate();
    }
}
