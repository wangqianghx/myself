package com.tools.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

/**
 * 描述：字符串工具类
 *
 * @author wangqiang at 2019/8/22 10:28
 */
public class StringUtil {

    /**
     * 描述：全角转半角
     *
     * @param inputStr 需要进行处理的字符串
     * @return java.lang.String 转换后字符串
     */
    public static String toDBC(@NotNull String inputStr) {
        if (StringUtils.isEmpty(inputStr)) {
            throw new NullPointerException("param:{inputStr} is null");
        }
        char c[] = inputStr.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 描述：中文符号转成对应英文符号
     *
     * @param inputStr 需要进行处理的字符串
     * @return java.lang.String 转换后字符串
     */
    public static String cn2us(String inputStr) {
        if (StringUtils.isEmpty(inputStr)) {
            throw new NullPointerException("param:{inputStr} is null");
        }
        // 定义中文符号数组
        String[] cnRegs = {"！", "，", "。", "；", "~", "《", "》", "（", "）", "？", "”", "｛", "｝", "“", "：", "【", "】", "”", "‘", "’"};
        // 定义英文符号数组
        String[] usRegs = {"!", ",", ".", ";", "`", "<", ">", "\\(", "\\)", "\\?", "'", "\\{", "}", "\"", ":", "\\{", "}", "\"", "\'", "\'"};
        String returnString = inputStr;
        // 按照数据的对应关系进行逐个符号替换
        for (int i = 0; i < cnRegs.length; i++) {
            returnString = returnString.replaceAll(cnRegs[i], usRegs[i]);
        }
        return returnString;
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        String reg = "^[+-]?[0-9]+(\\.[0-9]+)?$";
        return str.trim().matches(reg);
    }
}
