package com.jy.importservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @类名 ImportDataSourceNamesDto
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/20 12:08
 * @版本 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportDataSourceNamesDto {

    private String data_source_id;

    private String data_source_name;
}
