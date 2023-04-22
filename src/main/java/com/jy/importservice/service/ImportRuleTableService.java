package com.jy.importservice.service;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportRuleTable;

import java.util.List;

/**
 * @类名 ImportTableService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 18:40
 * @版本 1.0
 */
public interface ImportRuleTableService {

    ReturnVo queryByPage(Long pageNo, Long pageSize, String sourceSchema, String sourceTableName, String targetSchema, String targetTableName);

    ImportRuleTable queryById(Long id);

    void addRuleTable(ImportRuleTable importRuleTable) throws GlobalException;

    void modifyRuleTable(ImportRuleTable importRuleTable) throws GlobalException;

    void deleteRuleTable(Long id) throws GlobalException;

    List<ImportRuleTable> queryByRuleId(String ruleId);

    String queryTargetColumnListByRuleTableId(String ruleTableId);
}
