package com.jy.importservice.common.enums;

import lombok.Getter;

/**
 * @类名 TaskStatusEnums
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 14:57
 * @版本 1.0
 */
@Getter
public enum TaskStatusEnum {

    NOT_START(0, "未开始"), RUNNING(1, "执行中"), SUCCESS(2, "执行成功"), FAIL(3, "执行失败");

    private final int code;

    private final String msg;

    TaskStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
