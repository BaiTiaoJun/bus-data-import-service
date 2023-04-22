package com.jy.importservice.service;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportRule;

import java.util.List;

/**
 * @类名 ImportRuleService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 10:12
 * @版本 1.0
 */
public interface ImportRuleService {

    void addImportRule(ImportRule importRule) throws GlobalException;

    ReturnVo queryByPage(Long pageNo, Long pageSize, String ruleName, String sourceDatabase, String targetDatabase);

    ImportRule queryById(Long id);

    void modifyRule(ImportRule importRule) throws GlobalException;

    void deleteRule(Long id) throws GlobalException;

    List<ImportRule> queryAllNames();

    ImportRule selectSqlldrInfoIdByRuleId(String ruleId);
}
