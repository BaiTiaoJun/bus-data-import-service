package com.jy.importservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @类名 ImportStoreNamesDto
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 11:41
 * @版本 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportStoreDto {

    private String store_id;

    private String store_name;

    private String store_type;
}
