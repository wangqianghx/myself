package com.self.highlight_spring4.ch1.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wq
 * @date: 2018/7/19 13:50
 */
@Configuration
public class JavaConfig {
    public FunctionService functionService(){
        return new FunctionService();
    }

    @Bean
    public UseFunctionService useFunctionService(){
        UseFunctionService useFunctionService = new UseFunctionService();
        useFunctionService.setFunctionService(functionService());
        return useFunctionService;
    }

//    @Bean
//    public UseFunctionService useFunctionService(FunctionService functionService){
//        UseFunctionService useFunctionService = new UseFunctionService();
//        useFunctionService.setFunctionService(functionService);
//        return useFunctionService;
//    }
}
