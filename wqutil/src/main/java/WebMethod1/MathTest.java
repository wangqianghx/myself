package WebMethod1;

import util.SimpleArithmetic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MathTest {
    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);
        for (int i = 0; i <= 1; i++) {
            String expression = "1.222222*22.3214+(1.234-2.32123*(2.14478-1.23/2.34)+213.5)/12.35-45.21*51.23";
            double result = Calculator.conversion(expression);
            System.out.println(expression + " = " + result);
            System.out.println();
        }
        // 毫秒：1秒 = 1000 毫秒
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
    }
}
