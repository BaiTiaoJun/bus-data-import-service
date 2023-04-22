package com.jy.importservice.service.impl;

import com.jy.importservice.entity.ImportDirection;
import com.jy.importservice.mapper.ImportDirectionMapper;
import com.jy.importservice.service.ImportDirectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @类名 ImportDirectionServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 16:19
 * @版本 1.0
 */
@Service
public class ImportDirectionServiceImpl implements ImportDirectionService {

    @Resource
    private ImportDirectionMapper importDirectionMapper;
    
    @Override
    public List<ImportDirection> queryDicData(String type) {
        return importDirectionMapper.selectDicDataByType(type);
    }
}