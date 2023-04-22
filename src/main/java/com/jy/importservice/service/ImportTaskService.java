package com.jy.importservice.service;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportTask;
import org.quartz.SchedulerException;

/**
 * @类名 ImportTaskService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/18 19:07
 * @版本 1.0
 */
public interface ImportTaskService {

    ReturnVo queryByPage(Long pageNo, Long pageSize, String taskName, String taskType, String dataSource, String storeName, String taskStatus) throws GlobalException, InterruptedException;

    void addTask(ImportTask importTask) throws GlobalException, SchedulerException;

    ImportTask queryById(Long id);

    void modifyTask(ImportTask importTask) throws GlobalException, SchedulerException;

    void deleteTask(Long[] ids, String taskType) throws GlobalException, SchedulerException;

    void pauseTask(Long id) throws GlobalException, SchedulerException;

    void resumeTask(Long id) throws GlobalException, SchedulerException;

    ImportTask queryByTaskId(String taskId);

    void modifyTask(ImportTask importTask, Object... ignore) throws GlobalException;
}
