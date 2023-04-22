package com.jy.importservice.service;

import com.jy.importservice.common.dto.ImportDataSourceDto;
import com.jy.importservice.common.dto.ImportDataSourceNamesDto;
import com.jy.importservice.common.exception.GlobalException;

import java.util.List;

/**
 * @类名 ImportDataSourceService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 15:35
 * @版本 1.0
 */
public interface ImportDataSourceService {

    List<ImportDataSourceNamesDto> queryDataSourceNames() throws GlobalException;
}
