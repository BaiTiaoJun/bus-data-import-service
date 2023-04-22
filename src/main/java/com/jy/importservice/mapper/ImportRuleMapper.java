package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImportRuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImportRule record);

    int insertSelective(ImportRule record);

    ImportRule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportRule record);

    int updateByPrimaryKey(ImportRule record);

    List<ImportRule> selectByPage(@Param("pageNo") Long pageNo, @Param("pageSize") Long pageSize
            , @Param("ruleName") String ruleName, @Param("sourceDatabase") String sourceDatabase, @Param("targetDatabase") String targetDatabase);

    long selectTotalSize();

    ImportRule selectById(Long id);

    Integer lockResource(Long id);

    List<ImportRule> selectAllNames();

    ImportRule selectSqlldrInfoIdByRuleId(String ruleId);
}