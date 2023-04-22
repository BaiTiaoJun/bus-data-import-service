package com.jy.importservice.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @类名 ConvertUtil
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 12:34
 * @版本 1.0
 */
public class ConvertUtil {

    public static Map<String, Object> objectConvertMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(name);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(name, value);
        }
        return map;
    }
}
