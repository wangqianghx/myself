package com.tools.util;

import lombok.Data;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * 描述：
 *
 * @author wangqiang at 2019/8/27 11:29
 * @version 1.0.0
 */
@Data
@ToString
public class ReflectUtil {

    private String name;

    private String job;

    private int age;

    public static void main(String[] args){

        List list = new ArrayList();

        Map map = new HashMap();

        map.put("setName", "wq");
        map.put("setJob", "kaifa");
        map.put("setAge", 30);

        list.add(map);

        Method[] arrMethod = getMethods(ReflectUtil.class);

        ReflectUtil r = new ReflectUtil();

        for (Method method : arrMethod) {
            out.println(method.getName());
            if (map.containsKey(method.getName())){
                try {
                    out.println(method.getName());
                    method.invoke(r, map.get(method.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        out.println(r);
    }

    public static Method[] getMethods(Class T){

        return T.getMethods();
    }
}
