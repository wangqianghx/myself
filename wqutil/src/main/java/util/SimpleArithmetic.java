package util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 类描述：处理四则运算的简单处理类
 */
public final class SimpleArithmetic {

    /**
     * 测试方法
     */
    public static void main(String[] args) {

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i <= 1000; i++) {
            String expr = "1.255344532*(nav/(nav-nav*baccctejahioaitnv*fahuzwnnrze+nav/MQLPZEHTZBE)+MQLPZEHTZBE)+MQLPZEHTZBE*6-10/MQLPZEHTZBE";
            Map<String, String> map = new HashMap<String, String>();
            map.put("nav", "1.1");
            map.put("baccctejahioaitnv", "2.2");
            map.put("fahuzwnnrze", "3.3");
            map.put("MQLPZEHTZBE", "4.4");

            SimpleArithmetic simpleArithmetic = new SimpleArithmetic(expr, map, 100, BigDecimal.ROUND_HALF_EVEN);
            String result = simpleArithmetic.execArithmetic();
            System.out.println(result);
            System.out.println(i);
        }
        // 毫秒：1秒 = 1000 毫秒
        System.out.println(System.currentTimeMillis() - currentTimeMillis);
    }

    /**
     * 需要处理的计算公式
     * 公式中只能包含【数字、大小写英文字母、[+]、[-]、[*]、[/]、[.]】
     */
    private String expr;
    /**
     * 需要参与运算的真实数据，其中KEY值需要和[expr]公式中定义的参数相互对应，区分大小写
     */
    private Map<String, String> map;
    /**
     * 运算公式中涉及到除法的时候保留位数
     *
     * @see BigDecimal
     */
    private int scale;
    /**
     * 运算公式中设计到除法的时候保留位数方式
     *
     * @see BigDecimal
     */
    private int roundingMode;

    /**
     * 运算处理的基础构造器
     *
     * @param expr 计算公式
     * @param map  公式中具体参数值
     */
    public SimpleArithmetic(String expr, Map<String, String> map) {
        this(expr, map, 16, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 运算处理的可自定义构造器
     *
     * @param expr         计算公式
     * @param map          公式中具体参数值
     * @param scale        运算公式中涉及到除法的时候保留位数
     * @param roundingMode 运算公式中设计到除法的时候保留位数方式
     */
    public SimpleArithmetic(String expr, Map<String, String> map, int scale, int roundingMode) {
        if (roundingMode < BigDecimal.ROUND_UP || roundingMode > BigDecimal.ROUND_UNNECESSARY) {
            throw new IllegalArgumentException("Invalid rounding mode");
        }
        if (scale <= 0 || scale > 100) {
            throw new IllegalArgumentException("Invalid scale");
        }
        if (expr == null || "".equals(expr)) {
            throw new NullPointerException("expr is null");
        }
        if (!expr.matches("[()a-zA-Z0-9.+*/-]+")) {
            throw new IllegalArgumentException("Invalid expr");
        }
        this.expr = expr;
        this.map = map;
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    /**
     * 方法描述：提供处理的总入口
     *
     * @return String 返回处理结果
     */
    public String execArithmetic() {
        expr = formatExpression(expr, map);
        String result = operationExpression(expr, map);
        return expr.replace(expr, result);
    }

    /**
     * 方法描述：处理表达式中括号的处理，自循环调用直到把所有括号处理完毕
     *
     * @param exp 需要处理的表达式
     * @param map 表达式值的存储
     * @return String 进行解析处理之后的表达式
     */
    private String formatExpression(String exp, Map<String, String> map) {

        // 是否有括号的标识，如果有需要进行处理
        boolean flag = false;
        Matcher matcher = PatternEnum.formatExpression_pattern.getPatternSign().matcher(exp);
        int begin = 0, end = 0;
        // 循环得到最里层的括号，并设置标识
        while (matcher.find()) {
            String str = matcher.group(1);
            if (OperatorEnum.LEFT_PARENTHESIS.getOperatorSign().equals(str)) {
                begin = matcher.start();
            }
            if (OperatorEnum.RIGHT_PARENTHESIS.getOperatorSign().equals(str)) {
                end = matcher.start();
            }
            if (begin >= 0 && end > 0) {
                flag = true;
                break;
            }
        }
        // 根据对应的标识去处理两个括号之间的表达式计算
        if (flag) {
            // 得到最里层括号之间的表达式
            String exp1 = exp.substring(begin + 1, end);
            // 对上面得到的表达式进行计算
            String result = operationExpression(exp1, map);
            // 根据计算返回的结果去替换被计算的表达式
            exp = exp.replace(exp.substring(begin, end + 1), result);
            // 返回的结果都有正负之分，如果再原有括号前面是一个四则运算符的情况需要进行特殊的处理
            exp = execTwoOperator(exp);
            // 调用自身，循环处理括号
            exp = formatExpression(exp, map);
        }
        return exp;
    }

    /**
     * 方法描述：替换【*-、/-符号】
     *
     * @param exp 需要处理的表达式
     * @return String 进行解析处理之后的表达式
     */
    private String execIncludeOperator(String exp, String formOperator, String toOperator) {

        if (exp.contains(formOperator)) {
            // 得到需要替换源符号的具体位置
            int index = exp.indexOf(formOperator);
            // 进行字符串截取，根据上面的INDEX去截取前面一段的STRING
            String str = exp.substring(0, index);

            // 根据上面的STRING去分别得到这个字符串最后一个+、-、(的位置INDEX
            int a = str.lastIndexOf(OperatorEnum.ADD_SIGN.getOperatorSign());
            int b = str.lastIndexOf(OperatorEnum.MINUS_SIGN.getOperatorSign());
            int c = str.lastIndexOf(OperatorEnum.LEFT_PARENTHESIS.getOperatorSign());

            // 对上面得到的三个INDEX的位置进行关联性的判断及处理
            if (a > b && a > c) {// +号的位置是最后的时候
                exp = exp.substring(0, a) + OperatorEnum.MINUS_SIGN.getOperatorSign() + exp.substring(a + 1);
            }
            if (b > a && b > c) {// -号的位置是最后的时候
                exp = exp.substring(0, b) + OperatorEnum.ADD_SIGN.getOperatorSign() + exp.substring(b + 1);
            }
            if (c > a && c > b) {// (号的位置是最后的时候
                exp = exp.substring(0, c + 1) + OperatorEnum.MINUS_SIGN.getOperatorSign() + exp.substring(c + 1);
            }
            if (a == b && b == c) {// 三个符号的INDEX相等的时候，也就是前面这三个符合都不存在的时候
                exp = OperatorEnum.MINUS_SIGN.getOperatorSign() + exp;
            }
            exp = exp.replace(formOperator, toOperator);
        }
        return exp;
    }

    /**
     * 方法描述：当计算括号里面的表达式之后，如果括号前面是一个四则运算符那么需要进行特殊的处理。
     * 也就是说处理完括号之后会有连续两个操作符出现的情况。
     * <b>因为原有括号中的值是进行计算出来的所以只能是负数的时候才会出现这种情况。</b>
     * EG:
     * +-
     * --
     * *-
     * /-
     *
     * @param exp 需要处理的表达式
     * @return String 进行解析处理之后的表达式
     */
    private String execTwoOperator(String exp) {
        exp = exp.replace(OperatorEnum.MINUS_MINUS_SIGN.getOperatorSign(), OperatorEnum.ADD_SIGN.getOperatorSign())
                .replace(OperatorEnum.ADD_MINUS_SIGN.getOperatorSign(), OperatorEnum.MINUS_SIGN.getOperatorSign());
        exp = this.execIncludeOperator(exp, OperatorEnum.MULTIPLICATION_MINUS_SIGN.getOperatorSign(), OperatorEnum.MULTIPLICATION_SIGN.getOperatorSign());
        exp = this.execIncludeOperator(exp, OperatorEnum.DIVISION_MINUS_SIGN.getOperatorSign(), OperatorEnum.DIVISION_SIGN.getOperatorSign());
        return exp;
    }

    /**
     * 方法描述：对一串没有括号的表达式进行计算
     *
     * @param exp 需要处理的表达式
     * @param map 表达式值的存储
     * @return String 进行解析处理之后的表达式
     */
    private String operationExpression(String exp, Map<String, String> map) {
        // 乘法和除法的计算
        exp = multiplyOrDivideExpression(exp, map);
        // 加法和减法的计算
        exp = addOrSubtractExpression(exp, map);
        return exp;
    }

    /**
     * 方法描述：加法和减法的计算逻辑处理
     *
     * @param exp 需要处理的表达式
     * @param map 表达式值的存储
     * @return String 进行解析处理之后的表达式
     */
    private String addOrSubtractExpression(String exp, Map<String, String> map) {
        /*
         * 定义一个正则表达式，这个正则表达式匹配的是大小写字母任意数字以及加减号
         * 也就是匹配到a+b、a-b的这种表达式
         * 注：这里一定要加号和减号一起匹配，因为运算是有顺序的
         * */
        Matcher matcher = PatternEnum.addOrSubtractExpression_pattern.getPatternSign().matcher(exp);
        while (matcher.find()) {
            String str = matcher.group();
            // 得到减号的位置
            int indexNum = str.indexOf(OperatorEnum.MINUS_SIGN.getOperatorSign());
            if (exp.startsWith(OperatorEnum.MINUS_SIGN.getOperatorSign())) {
                str = OperatorEnum.MINUS_SIGN.getOperatorSign() + str;
            }
            // 定义结果值
            BigDecimal result;
            String beginStr, endStr, begin, end;
            // 如果匹配的表达式中有减号
            if (indexNum > 0) {
                beginStr = str.substring(0, indexNum);
                endStr = str.substring(indexNum + 1);
                // 截取符号前面的字符串，去判断是不是数字，如果是数字直接使用，如果不是数字去MAP中取到对应的值
                begin = isNumeric(beginStr) ? beginStr : map.get(beginStr);
                // 截取符号后面的字符串，去判断是不是数字，如果是数字直接使用，如果不是数字去MAP中取到对应的值
                end = isNumeric(endStr) ? endStr : map.get(endStr);
                // 根据得到的符号和符号前后的值去进行减法计算
                result = new BigDecimal(begin).subtract(new BigDecimal(end));
            } else {
                // 因为已经匹配到加号还是减号了，但是没有走上面的减号，那么匹配到的表达式中一定存在加号，在此基础上进行相关的处理
                indexNum = str.indexOf(OperatorEnum.ADD_SIGN.getOperatorSign());
                beginStr = str.substring(0, indexNum);
                endStr = str.substring(indexNum + 1);
                // 截取符号前面的字符串，去判断是不是数字，如果是数字直接使用，如果不是数字去MAP中取到对应的值
                begin = isNumeric(beginStr) ? beginStr : map.get(beginStr);
                // 截取符号后面的字符串，去判断是不是数字，如果是数字直接使用，如果不是数字去MAP中取到对应的值
                end = isNumeric(endStr) ? endStr : map.get(endStr);
                // 根据得到的符号和符号前后的值去进行加法计算
                result = new BigDecimal(begin).add(new BigDecimal(end));
            }
            // 把匹配掉的表达式用计算的结果替换掉
            exp = exp.replace(str, result.toString());
            // 自循环
            exp = addOrSubtractExpression(exp, map);
        }
        return exp;
    }

    /**
     * 方法描述：乘法和除法的计算逻辑处理
     *
     * @param exp 需要处理的表达式
     * @param map 表达式值的存储
     * @return String 进行解析处理之后的表达式
     */
    private String multiplyOrDivideExpression(String exp, Map<String, String> map) {
        /*
         * 具体的注释可以参照addOrSubtractExpression这个方法，他们的处理逻辑都是一样的
         * */
        Matcher matcher = PatternEnum.multiplyOrDivideExpression_pattern.getPatternSign().matcher(exp);
        while (matcher.find()) {
            String str = matcher.group();
            int indexNum = str.indexOf(OperatorEnum.MULTIPLICATION_SIGN.getOperatorSign());
            BigDecimal result;
            String beginStr, endStr, begin, end;
            if (indexNum > 0) {
                beginStr = str.substring(0, indexNum);
                endStr = str.substring(indexNum + 1);
                begin = isNumeric(beginStr) ? beginStr : map.get(beginStr);
                end = isNumeric(endStr) ? endStr : map.get(endStr);
                result = new BigDecimal(begin).multiply(new BigDecimal(end));
            } else {
                indexNum = str.indexOf(OperatorEnum.DIVISION_SIGN.getOperatorSign());
                beginStr = str.substring(0, indexNum);
                endStr = str.substring(indexNum + 1);
                begin = isNumeric(beginStr) ? beginStr : map.get(beginStr);
                end = isNumeric(endStr) ? endStr : map.get(endStr);
                result = new BigDecimal(begin).divide(new BigDecimal(end), this.scale, this.roundingMode);
            }
            exp = exp.replace(str, result.toString());
            exp = multiplyOrDivideExpression(exp, map);
        }
        return exp;
    }

    /**
     * 方法描述：对传入的字符串进行判断，校验是不是数值
     *
     * @param str 字符串
     * @return boolean 返回判断结果
     */
    private boolean isNumeric(String str) {
        return PatternEnum.isNumeric_pattern.getPatternSign().matcher(str).matches();
    }
}

/**
 * 描述：操作符枚举类
 */
enum OperatorEnum {

    /**
     * 左括号(
     */
    LEFT_PARENTHESIS("("),
    /**
     * 右括号)
     */
    RIGHT_PARENTHESIS(")"),
    /**
     * 加号+
     */
    ADD_SIGN("+"),
    /**
     * 减号-
     */
    MINUS_SIGN("-"),
    /**
     * 乘号*
     */
    MULTIPLICATION_SIGN("*"),
    /**
     * 除号/
     */
    DIVISION_SIGN("/"),
    /**
     * 加号减号+-
     */
    ADD_MINUS_SIGN("+-"),
    /**
     * 减号减号--
     */
    MINUS_MINUS_SIGN("--"),
    /**
     * 乘号减号*-
     */
    MULTIPLICATION_MINUS_SIGN("*-"),
    /**
     * 除号减号/-
     */
    DIVISION_MINUS_SIGN("/-");

    private String operatorSign;

    OperatorEnum(String operatorSign) {
        this.operatorSign = operatorSign;
    }

    public String getOperatorSign() {
        return operatorSign;
    }
}

/**
 * 描述：正则表达式枚举类
 */
enum PatternEnum {

    /**
     * ([()])
     */
    formatExpression_pattern(Pattern.compile("([()])")),
    /**
     * [a-zA-Z0-9]+(\.[0-9]+)?[-+][a-zA-Z0-9]+(\.[0-9]+)?
     */
    addOrSubtractExpression_pattern(Pattern.compile("[a-zA-Z0-9]+(\\.[0-9]+)?[-+][a-zA-Z0-9]+(\\.[0-9]+)?")),
    /**
     * 因注释原因此处用了转义符，注释和实际使用正则有差别
     * [a-zA-Z0-9]+(\.[0-9]+)?[*\/][a-zA-Z0-9]+(\.[0-9]+)?
     */
    multiplyOrDivideExpression_pattern(Pattern.compile("[a-zA-Z0-9]+(\\.[0-9]+)?[*/][a-zA-Z0-9]+(\\.[0-9]+)?")),
    /**
     * [-+]?[0-9]+(\.[0-9]+)?
     */
    isNumeric_pattern(Pattern.compile("[-+]?[0-9]+(\\.[0-9]+)?"));

    private Pattern patternSign;

    PatternEnum(Pattern patternSign) {
        this.patternSign = patternSign;
    }

    public Pattern getPatternSign() {
        return patternSign;
    }
}
