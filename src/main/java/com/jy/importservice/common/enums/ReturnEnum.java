package com.jy.importservice.common.enums;

import lombok.Getter;

/**
 * @类名 ReturnEnum
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/12 20:05
 * @版本 1.0
 */
@Getter
public enum ReturnEnum {

    SUCCESS(200, "success"), SERVER_ERROR(500, "server error"), PARAM_ERROR(400, "params error");

    private final int code;

    private final String msg;

    ReturnEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
