package com.tools.util;

import java.util.UUID;

import static java.lang.System.out;

/**
 * 描述：把对应32位的UUID转换成8位的字符串
 *
 * @author wangqiang at 2019/8/22 13:40
 */
public class UUIDUtil {

    /**
     * 定义一个数组
     */
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 描述：把生成的32位的UUID转换成8位的字符串
     *
     * @return 8位的UUID字符串
     * @author wangqiang at 2019/8/22 13:41
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
}
