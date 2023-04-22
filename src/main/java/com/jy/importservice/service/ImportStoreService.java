package com.jy.importservice.service;

import com.jy.importservice.common.dto.ImportStoreDto;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;

import java.util.List;

/**
 * @类名 ImportStoreService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 15:52
 * @版本 1.0
 */
public interface ImportStoreService {

    List<ImportStoreDto> queryAllNames() throws GlobalException;
}
