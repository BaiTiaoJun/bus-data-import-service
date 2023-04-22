package com.jy.importservice.controller;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportSubTask;
import com.jy.importservice.service.ImportSubTaskService;
import org.springframework.web.bind.annotation.*;

/**
 * @类名 ImportExecuteController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 17:54
 * @版本 1.0
 */
@RestController
@RequestMapping("/subtask")
public class ImportSubTaskController {

    private final ImportSubTaskService importSubTaskService;

    public ImportSubTaskController(ImportSubTaskService importSubTaskService) {
        this.importSubTaskService = importSubTaskService;
    }

    @GetMapping("/query-by-page")
    public ReturnVo queryByPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10") Long pageSize, String taskStatus) {
        return importSubTaskService.queryByPage(pageNo, pageSize, taskStatus);
    }

    @GetMapping("/details")
    public ReturnVo queryDetails(@RequestParam Long id, String tableName, String taskStatus) {
        ImportSubTask log = importSubTaskService.queryDetails(id, tableName, taskStatus);
        return ReturnVo.ok().put(log);
    }

    @DeleteMapping("/deletion")
    public ReturnVo deleteSubTask(@RequestParam Long id) throws GlobalException {
        importSubTaskService.deleteSubTask(id);
        return ReturnVo.ok();
    }
}
