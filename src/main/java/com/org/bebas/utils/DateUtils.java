package com.org.bebas.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 时间工具类
 *
 * @author WuHao
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";
    
    /**
     * 线程安全的日期格式化器缓存
     */
    private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();
    
    /**
     * 线程安全的SimpleDateFormat缓存
     */
    private static final ConcurrentMap<String, ThreadLocal<SimpleDateFormat>> DATE_FORMAT_CACHE = new ConcurrentHashMap<>();
    
    /**
     * 标准日期时间格式化器
     */
    public static final DateTimeFormatter NORM_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN);

    public static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前时间 输出字符串 默认 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间字符串
     */
    public static String nowDateFormat() {
        return LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .format(NORM_DATETIME_FORMATTER);
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(NORM_DATE_PATTERN);
    }

    public static String getTime() {
        return dateTimeNow(NORM_DATE_PATTERN);
    }

    public static String dateTimeNow() {
        return dateTimeNow(PURE_DATETIME_PATTERN);
    }

    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static String dateTime(final Date date) {
        return parseDateToStr(NORM_DATE_PATTERN, date);
    }

    public static String parseDateToStr(final String format, final Date date) {
        return getDateFormat(format).format(date);
    }

    public static Date dateTime(final String format, final String ts) {
        try {
            return getDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporalAccessor cannot be null");
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporalAccessor cannot be null");
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
    
    /**
     * 获取线程安全的DateTimeFormatter
     * @param pattern 日期格式
     * @return DateTimeFormatter实例
     */
    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        return FORMATTER_CACHE.computeIfAbsent(pattern, DateTimeFormatter::ofPattern);
    }
    
    /**
     * 获取线程安全的SimpleDateFormat
     * @param pattern 日期格式
     * @return SimpleDateFormat实例
     */
    public static SimpleDateFormat getDateFormat(String pattern) {
        return DATE_FORMAT_CACHE.computeIfAbsent(pattern, 
            p -> ThreadLocal.withInitial(() -> new SimpleDateFormat(p))).get();
    }
}