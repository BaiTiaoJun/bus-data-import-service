package com.jy.importservice.common.util;

/**
 * @类名 StringUtil
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 15:52
 * @版本 1.0
 */
public class StringUtil {

    public static String concat(StringBuffer stringBuffer, String... strings) {
        for (String string : strings) {
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}
