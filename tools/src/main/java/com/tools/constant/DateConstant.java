package com.tools.constant;

/**
 * 描述：时间处理的常量类</br>
 * 此类中主要定义各种常见的时间格式化字符串
 *
 * @author wangqiang at 2019/8/21 15:05
 * @version 1.0.0
 */
public class DateConstant {

    /**
     * 日期格式标识
     * 年月日 yyyyMMdd
     */
    public final static String PATTERN_SHORTDATE = "yyyyMMdd";

    /**
     * 日期格式标识
     * 年月日 yyyyMMddHHmmss
     */
    public final static String PATTERN_SHORTDATETIME = "yyyyMMddHHmmss";

    /**
     * 短日期格式标识
     * 年月日 yyyyMMddHHmmssSSS
     */
    public final static String PATTERN_LONGDATE = "yyyyMMddHHmmssSSS";

    /**
     * 时间类型格式标识
     * 年月日 yyyy-MM-dd
     */
    public final static String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * 时间类型格式标识
     * 时分秒 hh:mm:ss
     */
    public final static String PATTERN_TIME = "hh:mm:ss";

    /**
     * 时间类型格式标识
     * 年月日 时分秒 毫秒 yyyy-MM-dd HH:mm:ss.mmm
     */
    public final static String PATTERN_DATESTAMP = "yyyy-MM-dd HH:mm:ss.mmm";

    /**
     * 时间类型格式标识
     * 年月日 时分秒 yyyy-MM-dd HH:mm:ss
     */
    public final static String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间类型格式标识
     * 年月 yyyy-MM
     */
    public final static String PATTERN_MONTH = "yyyy-MM";

    /**
     * 时间类型格式标识
     * 年月 yyyy/MM/dd
     */
    public final static String PATTERN_DATE_CIB = "yyyy/MM/dd";
}
