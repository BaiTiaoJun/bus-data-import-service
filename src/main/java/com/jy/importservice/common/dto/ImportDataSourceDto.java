package com.jy.importservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @类名 ImportDataSourceDto
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 12:10
 * @版本 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportDataSourceDto extends ImportDataSourceNamesDto {

    private Long id;

    private String jdbc_url;

    private String password;

    private String username;

    private Date update_time;

    private Integer city_code;
}
