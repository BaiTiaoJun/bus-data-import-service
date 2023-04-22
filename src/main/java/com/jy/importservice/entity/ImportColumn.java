package com.jy.importservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @类名 ImportColumn
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/18 14:30
 * @版本 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportColumn {

    private String column;

    private String columnType;

    private String columnFormat;
}
