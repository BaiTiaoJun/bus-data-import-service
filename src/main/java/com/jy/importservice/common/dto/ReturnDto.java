package com.jy.importservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @类名 ReturnDto
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 12:56
 * @版本 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDto {

    private int status;

    private String msg;

    private Object data;
}
