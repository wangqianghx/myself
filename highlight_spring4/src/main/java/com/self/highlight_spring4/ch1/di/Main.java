package com.self.highlight_spring4.ch1.di;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

/**
 * @author: wq
 * @date: 2018/7/19 13:42
 */

public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{-3, 4, 3, 90};
        int target = 0;
        Map targetNums = new HashMap();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (targetNums.get(target - nums[i]) != null) {
                res[0] = (int) targetNums.get(target - nums[i]);
                res[1] = i;
                break;
            }
            targetNums.put(nums[i], i);
        }
        out.println(res[0] + "===" + res[1]);


//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiConfig.class);
//        UseFunctionService useFunctionService = context.getBean(UseFunctionService.class);
//        String result = useFunctionService.SayHello("di");
//        System.out.println(result);
//        context.close();
    }
}
