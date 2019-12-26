package com.tools.todo;

import com.tools.annotation.VoMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：VO和PO互转，禁止高性能批量数据处理的时候使用，仅限于增删改查
 *
 * @author zhaowen at 2018/12/13 16:50
 * @version 1.0.0
 */
public class BeanCopy {
    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }


    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 描述：复制数据
     *
     * @param source 源
     * @param target 目标
     * @param noCopy 隔离的字段
     * @return void
     * @author zhaowen at 2018/12/25 16:30
     */
    public static void copy(Object source, Object target, String... noCopy) {
        try {
            Class c = target.getClass();
            Class c1 = source.getClass();
            Field[] f = c.getDeclaredFields();
            Map<String, String> map = new HashMap<String, String>();
            //获取全局参数变量
            for (Field field : f) {
                if (field.isAnnotationPresent(VoMapper.class)) {
                    VoMapper w = field.getAnnotation(VoMapper.class);
                    String[] className = w.fromClass().split(",");
                    String[] cloumnName = w.fromPrototype().split(",");
                    for (int j = 0; j < className.length; j++) {
                        if (source.getClass().getName().toString().trim().contains(className[j].trim())) {
                            if (noCopy == null || noCopy.length == 0) {
                                map.put(field.getName().toUpperCase().trim(), cloumnName[j].toUpperCase().trim());
                            } else {
                                boolean flag = true;
                                for (int x = 0; x < noCopy.length; x++) {
                                    if (noCopy[x].toUpperCase().equals(field.getName().toUpperCase())) {
                                        flag = false;
                                    }

                                }
                                if (flag) {
                                    map.put(field.getName().toUpperCase().trim(), cloumnName[j].toUpperCase().trim());
                                }
                            }

                        }

                    }

                }
            }

            Method[] methods = c.getMethods();
            Method[] methods1 = c1.getMethods();
            for (Method method : methods) {
                String name = method.getName().substring(3, method.getName().length()).trim().toString().toUpperCase();
                if (method.getName().substring(0,3).toUpperCase().contains("SET") && map.get(name) != null) {
                    for (Method method1 : methods1) {
                        if (method1.getName().toUpperCase().equals("GET" + map.get(name))) {
                            Object obj = method1.invoke(source);
                            //将null转换为空字符串
                            if (method1.getGenericReturnType().toString().contains("java.lang.String") && obj == null) {
                                obj = "".trim();
                            }
                            if (method1.getGenericReturnType().toString().contains("java.lang.Integer") && obj == null) {
                                obj = 0;
                            }
                            method.invoke(target, obj);

                        }
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
