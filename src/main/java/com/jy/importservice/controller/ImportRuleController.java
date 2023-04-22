package com.jy.importservice.controller;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportRule;
import com.jy.importservice.service.ImportRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @类名 ImportRuleController
 * @描述 导入规则API
 * @作者 白条君
 * @创建日期 2023/4/16 23:16
 * @版本 1.0
 */
@RestController
@RequestMapping("/rule")
public class ImportRuleController {

    private final ImportRuleService importRuleService;

    public ImportRuleController(ImportRuleService importRuleService) {
        this.importRuleService = importRuleService;
    }

    @GetMapping("/query-by-page")
    public ReturnVo queryByPage(@RequestParam(defaultValue = "1") Long pageNo
            , @RequestParam(defaultValue = "10") Long pageSize
            , String ruleName, String sourceDatabase, String targetDatabase) {
        return importRuleService.queryByPage(pageNo, pageSize, ruleName, sourceDatabase, targetDatabase);
    }

    @GetMapping("/query-by-id")
    public ReturnVo queryById(@RequestParam Long id) {
        ImportRule importRule = importRuleService.queryById(id);
        return ReturnVo.ok().put(importRule);
    }

    @GetMapping("/query-all-names")
    public ReturnVo queryAllNames() {
        List<ImportRule> importRules = importRuleService.queryAllNames();
        return ReturnVo.ok().put(importRules);
    }

    @PostMapping("/addition")
    public ReturnVo add(@RequestBody ImportRule importRule) throws GlobalException {
        importRuleService.addImportRule(importRule);
        return ReturnVo.ok();
    }

    @PutMapping("/modification")
    public ReturnVo modify(@RequestBody ImportRule importRule) throws GlobalException {
        importRuleService.modifyRule(importRule);
        return ReturnVo.ok();
    }

    @DeleteMapping("/deletion")
    public ReturnVo deletion(@RequestParam Long id) throws GlobalException {
        importRuleService.deleteRule(id);
        return ReturnVo.ok();
    }
}
