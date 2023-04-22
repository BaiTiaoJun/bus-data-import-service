package com.jy.importservice.service;

import com.jy.importservice.entity.ImportDirection;

import java.util.List;

/**
 * @类名 ImportDirectionService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 16:19
 * @版本 1.0
 */
public interface ImportDirectionService {

    List<ImportDirection> queryDicData(String type);
}
