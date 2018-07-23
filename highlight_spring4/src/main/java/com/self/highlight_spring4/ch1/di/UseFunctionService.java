package com.self.highlight_spring4.ch1.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: wq
 * @date: 2018/7/19 13:40
 */
@Service
public class UseFunctionService {
    @Autowired
    FunctionService functionService;

    public String SayHello(String word){
        return functionService.sayHello(word);
    }
}
