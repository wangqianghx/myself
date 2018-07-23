package com.self.highlight_spring4.ch1.javaconfig;

/**
 * @author: wq
 * @date: 2018/7/19 13:48
 */
public class UseFunctionService {

    FunctionService functionService;

    public void setFunctionService(FunctionService functionService){
        this.functionService = functionService;
    }

    public String SayHello(String word){
        return functionService.sayHello(word);
    }
}
