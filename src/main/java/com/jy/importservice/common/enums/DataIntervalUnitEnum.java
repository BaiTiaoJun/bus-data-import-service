package com.jy.importservice.common.enums;

import lombok.Getter;

/**
 * @类名 DataIntervalUnitEnum
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/21 17:01
 * @版本 1.0
 */
@Getter
public enum DataIntervalUnitEnum {

    MONTH(0, "月"), DAY(1, "日"), HOUR(2, "时"), MINUTES(3, "分");

    private final int code;

    private final String msg;

    private DataIntervalUnitEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
