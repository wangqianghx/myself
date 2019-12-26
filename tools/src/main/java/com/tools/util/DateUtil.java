package com.tools.util;

import com.tools.constant.DateConstant;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 描述：日期处理类
 *
 * @author wangqiang at 2019/8/22 13:11
 */
public class DateUtil {

    /**
     * 定义一个全局的Calendar实例，在具体的方法中会重新定义时间
     */
    private static Calendar calendar = Calendar.getInstance();

    /**
     * 字符串转化成日期
     *
     * @param dateStr 要转化的日期字符串：非空
     * @param pattern 字符串对应的日期格式：可为空，如果传入空值那么默认值为“yyyy-MM-dd HH:mm:ss”
     * @return Date 返回一个日期
     */
    public static Date string2Date(@NotNull String dateStr, String pattern){
        if (StringUtils.isEmpty(dateStr)){
            throw new NullPointerException("param:{dateStr} is null");
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = DateConstant.PATTERN_DATETIME;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期转化为字符串
     *
     * @param date    要转化的日期
     * @param pattern 指定的转化格式
     * @return
     */
    public static String date2String(@NotNull Date date, String pattern) {
        if (date == null) {
            throw new NullPointerException("param:{date} is null");
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = DateConstant.PATTERN_DATETIME;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前年份
     */
    public static int getYear() {
        return getYear(new Date());
    }

    /**
     * 获取指定日期年份
     *
     * @param date 指定时间
     */
    public static int getYear(Date date) {
        DateUtil.calendar = Calendar.getInstance();
        DateUtil.calendar.setTime(date);
        return DateUtil.calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     */
    public static int getMonth() {
        return getMonth(new Date());
    }

    /**
     * 获取指定日期月份
     *
     * @param date 指定时间
     */
    public static int getMonth(Date date) {
        DateUtil.calendar = Calendar.getInstance();
        DateUtil.calendar.setTime(date);
        return DateUtil.calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当月第几天
     */
    public static int getDay() {
        return getDay(new Date());
    }

    /**
     * 获取指定日期天
     *
     * @param date 指定时间
     */
    public static int getDay(Date date) {
        DateUtil.calendar = Calendar.getInstance();
        DateUtil.calendar.setTime(date);
        return DateUtil.calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取本月第一天的时间
     */
    public static Date getMonthOfStart() {
        DateUtil.calendar.setTime(new Date());
        DateUtil.calendar.set(DateUtil.calendar.get(Calendar.YEAR), DateUtil.calendar.get(Calendar.MONTH), 1);
        return DateUtil.calendar.getTime();
    }

    /**
     * 获取下月第一天的时间
     */
    public static Date getNextMonthOfStart() {
        DateUtil.calendar.setTime(new Date());
        DateUtil.calendar.set(DateUtil.calendar.get(Calendar.YEAR), DateUtil.calendar.get(Calendar.MONTH) + 1, 1);
        return DateUtil.calendar.getTime();
    }

    /**
     * 获取本月最后一天的时间
     */
    public static Date getMonthOfEnd() {
        DateUtil.calendar.setTime(new Date());
        DateUtil.calendar.set(DateUtil.calendar.get(Calendar.YEAR), DateUtil.calendar.get(Calendar.MONTH) + 1, 0);
        return DateUtil.calendar.getTime();
    }

    /**
     * 描述：获取系统时间是几月几号
     *
     * @return MM-dd格式的字符串
     */
    public static String getMonthOfDay() {
        DateUtil.calendar.setTime(new Date());
        int month = DateUtil.calendar.get(Calendar.MONTH) + 1;
        int day = DateUtil.calendar.get(Calendar.DAY_OF_MONTH);
        return month + "-" + day;
    }

    /**
     * 描述：获取指定月份的天数
     *
     * @param monthOfYear yyyy-MM类型的日期
     * @return 指定年月的天数
     */
    public static int getMonthDay(String monthOfYear) {
        if (StringUtils.isEmpty(monthOfYear)) {
            throw new NullPointerException("param:{monthOfYear} is null");
        }
        Calendar calendar = Calendar.getInstance();
        Date date = string2Date(monthOfYear, DateConstant.PATTERN_MONTH);
        calendar.setTime(date);
        //把日期设置为当月第一天
        calendar.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }
}
