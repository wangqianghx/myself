package com.self.wq.util;

import java.io.Serializable;
import java.math.BigDecimal;

public class Test {

   public static void main(String[] args ){

       BigDecimal b = new BigDecimal("-123");

       System.out.println(b.add(new BigDecimal(23)));
   }
}
