package com.self.wq.utilstack;

import java.util.Collections;
import java.util.Stack;

/**
 * 解析公式--算数表达式解析
 * 直接调用 AnalyticFormula 的类方法 conversion()
 * 传入算数表达式，将返回一个解析后的表达式栈
 * 如果计算过程错误，将返回一个NaN
 * <p>
 * 注：此处处理的算术表达式不考虑公式中包含科学技术法，以及参与公式中实际的值存在负号
 * EG:-2+-1*(-3E-2)-(-1)
 */
public class AnalyticFormula {

    public Stack<String> conversion(String expression) {
        Stack<String> result;
        try {
            result = prepare(expression);
        } catch (Exception e) {
            e.printStackTrace();
            // 运算错误返回NaN
            return null;
        }
        return result;
    }

    /**
     * 数据准备阶段将表达式转换成为后缀式栈
     *
     * @param expression
     */
    private Stack<String> prepare(String expression) {

        // 后缀式栈
        // 作为左后返回结果，进行后续计算处理
        Stack<String> postfixStack = new Stack<String>();

        // 运算符栈
        // 中间处理运算符的临时栈，中间进行操作符优先级处理
        Stack<Character> opStack = new Stack<Character>();

        opStack.push(',');// 运算符放入栈底元素逗号，此符号优先级最低
        char[] arr = expression.toCharArray();
        int currentIndex = 0;// 当前字符的位置
        int count = 0;// 上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
        char currentOp, peekOp;// 当前操作符和栈顶操作符
        for (int i = 0; i < arr.length; i++) {
            currentOp = arr[i];
            if (ArithHelper.isOperator(currentOp)) {// 如果当前字符是运算符
                if (count > 0) {
                    postfixStack.push(new String(arr, currentIndex, count));// 取两个运算符之间的数字
                }
                peekOp = opStack.peek();
                // 遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
                if (currentOp == ')') {
                    while (opStack.peek() != '(') {
                        postfixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else {
                    while (currentOp != '(' && peekOp != ',' && ArithHelper.compare(currentOp, peekOp)) {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i + 1;
            } else {
                count++;
            }
        }
        // 最后一个字符不是括号或者其他运算符的则加入后缀式栈中
        if (count > 1 || (count == 1 && !ArithHelper.isOperator(arr[currentIndex]))) {
            postfixStack.push(new String(arr, currentIndex, count));
        }
        // 将操作符栈中的剩余的元素添加到后缀式栈中
        while (opStack.peek() != ',') {
            postfixStack.push(String.valueOf(opStack.pop()));
        }
        // 将后缀式栈反转
        Collections.reverse(postfixStack);
        return postfixStack;
    }
}
