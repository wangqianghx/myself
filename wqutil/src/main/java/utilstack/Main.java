package utilstack;

import WebMethod1.Calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author: wq
 * @date: 2018/8/29 9:46
 */
public class Main {
    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        String expr = "1.255344532*(nav/(nav-nav*baccctejahioaitnv*fahuzwnnrze+nav/MQLPZEHTZBE)+MQLPZEHTZBE)+MQLPZEHTZBE*6-10/MQLPZEHTZBE";
        Stack<String> s = new AnalyticFormula().conversion(expr);
        for (int i = 0; i <= 10000000; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("nav", "1.1");
            map.put("baccctejahioaitnv", "2.2");
            map.put("fahuzwnnrze", "3.3");
            map.put("MQLPZEHTZBE", "4.4");
            String result = new FormulaComputing().calculate((Stack<String>)s.clone(), map);

            System.out.println(expr + " = " + result);
            System.out.println();
        }
        // 毫秒：1秒 = 1000 毫秒
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
    }
}
