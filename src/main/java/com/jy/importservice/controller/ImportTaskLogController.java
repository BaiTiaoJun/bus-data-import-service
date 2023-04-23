package com.jy.importservice.controller;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportTaskLog;
import com.jy.importservice.service.ImportTaskLogService;
import org.springframework.web.bind.annotation.*;

/**
 * @类名 ImportExecuteController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 17:54
 * @版本 1.0
 */
@RestController
@RequestMapping("/log")
public class ImportTaskLogController {

    private final ImportTaskLogService importTaskLogService;

    public ImportTaskLogController(ImportTaskLogService importTaskLogService) {
        this.importTaskLogService = importTaskLogService;
    }

    @GetMapping("/query-by-page")
    public ReturnVo queryByPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10") Long pageSize, String taskStatus) {
        return importTaskLogService.queryByPage(pageNo, pageSize, taskStatus);
    }

    @GetMapping("/details")
    public ReturnVo queryDetails(@RequestParam Long id, String tableName, String taskStatus) {
        ImportTaskLog log = importTaskLogService.queryDetails(id, tableName, taskStatus);
        return ReturnVo.ok().put(log);
    }

    @DeleteMapping("/deletion")
    public ReturnVo deleteTaskLog(@RequestParam Long id) throws GlobalException {
        importTaskLogService.deleteTaskLog(id);
        return ReturnVo.ok();
    }
}
