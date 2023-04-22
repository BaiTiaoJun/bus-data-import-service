package com.jy.importservice.common.enums;

import lombok.Getter;

/**
 * @类名 TaskLoadType
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/20 16:31
 * @版本 1.0
 */
@Getter
public enum TaskTypeEnum {

    AUTO_EXECUTE(0, "自动执行"), HAND_EXECUTE(1, "手动执行");

    private final int code;

    private final String msg;

    TaskTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
