package com.jy.importservice.service;


import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.entity.ImportSqlldrInfo;
import com.jy.importservice.common.vo.ReturnVo;

import java.util.List;

/**
 * @类名 ImportDataSourceService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/12 20:01
 * @版本 1.0
 */
public interface ImportSqlldrInfoService {

    ReturnVo queryByPage(Long pageNo, Long pageSize, String sqlldrInfoName);

    List<ImportSqlldrInfo> queryAllNames();

    void addSqlldrInfo(ImportSqlldrInfo importSqlldrInfo) throws GlobalException;

    void modifySqlldrInfo(ImportSqlldrInfo importSqlldrInfo) throws GlobalException;

    void deleteSqlldrInfo(Long id) throws GlobalException;
}
