package utilstack;

import com.sun.deploy.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Stack;

/**
 * 对处理后的算术表达式栈和相应公式中的值进行计算处理，并返回结果
 */
public class FormulaComputing {

    /**
     * 按照给定的表达式计算
     *
     * @param expression 要计算的表达式例如:5+12*(3+5)/7
     * @return
     */
    public String calculate(Stack<String> postfixStack, Map<String, String> valueMap) {
        Stack<String> resultStack = new Stack<String>();
        String firstValue, secondValue, currentValue;// 参与计算的第一个值，第二个值和算术运算符
        while (!postfixStack.isEmpty()) {
            currentValue = postfixStack.pop();
            if (!ArithHelper.isOperator(currentValue.charAt(0))) {// 如果不是运算符则存入操作数栈中
                resultStack.push(currentValue);
            } else {// 如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                secondValue = resultStack.pop();
                firstValue = resultStack.pop();

                secondValue = valueMap.get(secondValue) == null || "".equals(valueMap.get(secondValue)) ? secondValue : valueMap.get(secondValue);
                firstValue = valueMap.get(firstValue) == null || "".equals(valueMap.get(firstValue)) ? firstValue : valueMap.get(firstValue);

                String tempResult = ArithHelper.calculate(firstValue, secondValue, currentValue.charAt(0));
                resultStack.push(tempResult);
            }
        }
        return resultStack.pop();
    }
}
