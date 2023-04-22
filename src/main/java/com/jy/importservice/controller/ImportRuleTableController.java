package com.jy.importservice.controller;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportRuleTable;
import com.jy.importservice.service.ImportRuleTableService;
import org.springframework.web.bind.annotation.*;

/**
 * @类名 ImpoertTableController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 18:38
 * @版本 1.0
 */
@RestController
@RequestMapping("/rule/table")
public class ImportRuleTableController {

    private final ImportRuleTableService importRuleTableService;

    public ImportRuleTableController(ImportRuleTableService importRuleTableService) {
        this.importRuleTableService = importRuleTableService;
    }

    @GetMapping("/query-by-page")
    public ReturnVo queryByPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10") Long pageSize,
                                String sourceSchema, String sourceTableName, String targetSchema, String targetTableName) {
        return importRuleTableService.queryByPage(pageNo, pageSize, sourceSchema, sourceTableName, targetSchema, targetTableName);
    }

    @GetMapping("/query-by-id")
    public ReturnVo queryById(@RequestParam Long id) {
        ImportRuleTable importRuleTable = importRuleTableService.queryById(id);
        return ReturnVo.ok().put(importRuleTable);
    }

    @PostMapping("/addition")
    public ReturnVo add(@RequestBody ImportRuleTable importRuleTable) throws GlobalException {
        importRuleTableService.addRuleTable(importRuleTable);
        return ReturnVo.ok();
    }

    @PutMapping("/modification")
    public ReturnVo modify(@RequestBody ImportRuleTable importRuleTable) throws GlobalException {
        importRuleTableService.modifyRuleTable(importRuleTable);
        return ReturnVo.ok();
    }

    @DeleteMapping("/deletion")
    public ReturnVo delete(@RequestParam Long id) throws GlobalException {
        importRuleTableService.deleteRuleTable(id);
        return ReturnVo.ok();
    }
}
