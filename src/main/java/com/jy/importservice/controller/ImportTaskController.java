package com.jy.importservice.controller;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportTask;
import com.jy.importservice.service.ImportTaskService;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

/**
 * @类名 ImportTaskController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/18 19:06
 * @版本 1.0
 */
@RestController
@RequestMapping("/task")
public class ImportTaskController {

    private final ImportTaskService importTaskService;

    public ImportTaskController(ImportTaskService importTaskService) {
        this.importTaskService = importTaskService;
    }

    @GetMapping("/query-by-page")
    public ReturnVo queryByPage(@RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "10") Long pageSize, String taskName,
                                String taskType, String dataSource, String storeName, String taskStatus) throws GlobalException, InterruptedException {
        return importTaskService.queryByPage(pageNo, pageSize, taskName, taskType, dataSource, storeName, taskStatus);
    }

    @PostMapping("/addition")
    public ReturnVo addTask(@RequestBody ImportTask importTask) throws GlobalException, SchedulerException {
        importTaskService.addTask(importTask);
        return ReturnVo.ok();
    }

    @GetMapping("/query-by-id")
    public ReturnVo queryById(@RequestParam Long id) {
        ImportTask importTask = importTaskService.queryById(id);
        return ReturnVo.ok().put(importTask);
    }

    @PutMapping("/modification")
    public ReturnVo modifyTask(@RequestBody ImportTask importTask) throws GlobalException, SchedulerException {
        importTaskService.modifyTask(importTask);
        return ReturnVo.ok();
    }

    @DeleteMapping("/deletion")
    public ReturnVo deleteTask(@RequestParam Long[] ids, @RequestParam String taskType) throws GlobalException, SchedulerException {
        importTaskService.deleteTask(ids, taskType);
        return ReturnVo.ok();
    }

    @PutMapping("/pause")
    public ReturnVo pauseTask(@RequestParam Long id) throws SchedulerException, GlobalException {
        importTaskService.pauseTask(id);
        return ReturnVo.ok();
    }

    @PutMapping("/resume")
    public ReturnVo resumeTask(@RequestParam Long id) throws SchedulerException, GlobalException {
        importTaskService.resumeTask(id);
        return ReturnVo.ok();
    }

    @PostMapping("/start")
    public ReturnVo handStart() {
        return ReturnVo.ok();
    }
}
