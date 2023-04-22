package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportSqlldrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImportSqlldrInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImportSqlldrInfo record);

    int insertSelective(ImportSqlldrInfo record);

    ImportSqlldrInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportSqlldrInfo record);

    int updateByPrimaryKey(ImportSqlldrInfo record);

    List<ImportSqlldrInfo> selectByPage(@Param("pageNo") Long pageNo, @Param("pageSize") Long pageSize, @Param("sqlldrInfoName") String sqlldrInfoName);

    List<ImportSqlldrInfo> selectAllNames();

    Integer lockResource(Long id);

    long selectTotalSize();
}