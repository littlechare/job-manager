package com.littlehow.job.base.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateUtil {

    public static final int HOUR = 1000 * 60 * 60;

    public static final long DAY = HOUR * 24;

    public static final int NANO = 1000000;
    private static final DateTimeFormatter DATE_FORMAT_NO_SPLIT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter DATE_FORMAT_DOT_SPLIT = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public static final DateTimeFormatter DATE_FORMAT_HAS_SPLIT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ALL = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static int zoneOffsetMilliseconds = TimeZone.getDefault().getRawOffset();
    private static ZoneOffset zoneOffset = ZoneOffset.ofHours(zoneOffsetMilliseconds / HOUR);

    /**
     * 设置全局时区
     *
     * @param hour -
     */
    public static void setZoneOffset(int hour) {
        zoneOffset = ZoneOffset.ofHours(hour);
        zoneOffsetMilliseconds = hour * HOUR;
    }

    public static long getTime(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(zoneOffset) * 1000L + localDateTime.getNano() / NANO;
    }

    public static long getTime(LocalDate localDate) {
        return localDate.toEpochDay() * DAY - zoneOffsetMilliseconds;
    }

    public static LocalDateTime toLocalDateTime(long milliseconds) {
        long second = milliseconds / 1000;
        long na = (milliseconds % 1000) * NANO;
        return LocalDateTime.ofEpochSecond(second, (int) na, zoneOffset);
    }

    public static LocalDate toLocalDate(long milliseconds) {
        return LocalDate.ofEpochDay((milliseconds + zoneOffsetMilliseconds) / DAY);
    }

    public static LocalDateTime toLocalDateTime(String datetime) {
        return LocalDateTime.parse(datetime, ALL);
    }

    public static String formatDate(LocalDate localDate) {
        return localDate.format(DATE_FORMAT_NO_SPLIT);
    }

    public static String formatDate(LocalDateTime localDateTime) {
        return localDateTime.format(ALL);
    }

    public static boolean isYesterday(LocalDate localDate) {
        return localDate != null && localDate.plusDays(1).compareTo(LocalDate.now()) == 0;
    }

    public static LocalDateTime toZone(LocalDateTime time, ZoneId fromZone, ZoneId toZone) {
        return time.atZone(fromZone).withZoneSameInstant(toZone).toLocalDateTime();
    }

    public static LocalDateTime toZone(LocalDateTime time, ZoneId toZone) {
        return toZone(time, zoneOffset, toZone);
    }

    public static LocalDateTime toZone(LocalDateTime time, String zoneId) {
        try {
            if (StringUtils.hasText(zoneId)) {
                return toZone(time, ZoneId.of(zoneId));
            } else {
                return time;
            }
        } catch (Exception e) {
            return time;
        }
    }

    public static LocalDate parseDot(String date) {
        if (StringUtils.hasText(date)) {
            return LocalDate.parse(date, DATE_FORMAT_DOT_SPLIT);
        }
        return null;
    }

    public static void main(String[] argv) {
        System.out.println(toLocalDateTime(1629300273000L));
        System.out.println(getTime(LocalDate.now()));
        LocalDate date = toLocalDate(1631116800000L);
        LocalDate end = LocalDate.now();
        System.out.println(date);
//        checkDateRange(date, end, 30);
//        System.out.println(ZoneId.of("+08:00"));
//        System.out.println(ZoneId.of("-11:00"));
    }
}
