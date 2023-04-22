package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportDirection;

import java.util.List;

public interface ImportDirectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImportDirection record);

    int insertSelective(ImportDirection record);

    ImportDirection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportDirection record);

    int updateByPrimaryKey(ImportDirection record);

    List<ImportDirection> selectDicDataByType(String type);
}