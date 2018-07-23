package com.self.highlight_spring4.ch1.di;

import org.springframework.stereotype.Service;

/**
 * @author: wq
 * @date: 2018/7/19 13:38
 */
@Service
public class FunctionService {
    public String sayHello(String word){
        return "Hello " + word + " !";
    }
}
