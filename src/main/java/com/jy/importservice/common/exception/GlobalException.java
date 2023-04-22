package com.jy.importservice.common.exception;

import lombok.Getter;

/**
 * @类名 GlobalException
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/16 21:54
 * @版本 1.0
 */
@Getter
public class GlobalException extends Exception {

    int code;

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GlobalException(String message) {
        super(message);
    }
}
