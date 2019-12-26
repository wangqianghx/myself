package com.tools.annotation;

/**
 * 描述：映射注解类
 *
 * @author zhaowen at 2018/12/25 13:34
 * @version 1.0.0
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * VO与PO映射注解
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface VoMapper {
   /**
    * 描述：类名
    *
    * @param
    * @return java.lang.String
    * @author zhaowen at 2018/12/25 13:39
    */
    public String fromClass() default "";

    /**
     * 描述：字段名
     *
     * @param
     * @return java.lang.String
     * @author zhaowen at 2018/12/25 13:39
     */
    public String fromPrototype() default "";
}