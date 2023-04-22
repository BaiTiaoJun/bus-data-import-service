package com.jy.importservice.common.util;

/**
 * @类名 PageUtil
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 17:09
 * @版本 1.0
 */
public class PageUtil {

    public static long getTotalPage(long totalSize, long pageSize) {
        long size = totalSize / pageSize;
        return totalSize % pageSize == 0? size == 0? 1 : size : size + 1;
    }
}
