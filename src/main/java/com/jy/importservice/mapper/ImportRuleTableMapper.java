package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportRuleTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImportRuleTableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImportRuleTable record);

    int insertSelective(ImportRuleTable record);

    ImportRuleTable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportRuleTable record);

    int updateByPrimaryKeyWithBLOBs(ImportRuleTable record);

    int updateByPrimaryKey(ImportRuleTable record);

    List<ImportRuleTable> selectTableByPage(@Param("pageNo") Long pageNo, @Param("pageSize") Long pageSize, @Param("sourceSchema") String sourceSchema,
                                        @Param("sourceTableName") String sourceTableName, @Param("targetSchema") String targetSchema, @Param("targetTableName") String targetTableName);

    long selectTableTotalSize();

    Integer lockResource(Long id);

    List<ImportRuleTable> selectByRuleId(String ruleId);

    String selectTargetColumnListByRuleTableId(String ruleTableId);
}