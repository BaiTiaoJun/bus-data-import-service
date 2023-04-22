package com.jy.importservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @类名 RuleTblDto
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/18 11:35
 * @版本 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleTblDto {

    private List<String> ruleTblList;

    private Long ruleTblCount;
}
