package com.self.highlight_spring4.ch1.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: wq
 * @date: 2018/7/19 13:42
 */
public class Main {
    public static void main(String[] args){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiConfig.class);
        UseFunctionService useFunctionService = context.getBean(UseFunctionService.class);
        String result = useFunctionService.SayHello("di");
        System.out.println(result);
        context.close();
    }
}
