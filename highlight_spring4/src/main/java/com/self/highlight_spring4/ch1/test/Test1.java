package com.self.highlight_spring4.ch1.test;

import static java.lang.System.out;

/**
 * 描述：
 *
 * @author wangqiang at 2019/12/24 11:44
 * @version 1.0.0
 */
public class Test1 {

    public static void main(String[] args){

        int m  = 51;
        int n = 9;


        out.println("1     " + test(m,n));
        out.println("2     " + test1(m,n));
    }

    public static int test1(int m, int n){

        if (m <= 1 || n <= 1){
            return 1;
        }

        int res=0;

        for(int i = n; i >= 1; i--){
            res += 2 + m - 3;
        }

        return res;
    }

    public static int test(int m, int n){

        int res = 0;

        if (m == 1 || n == 1){
            return 1;
        }

        res += test(m -1 , n);
        res += test(m  , n-1);
        return res;
    }
}
