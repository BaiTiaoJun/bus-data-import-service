package com.jy.importservice.service;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.entity.ImportSubTask;

import java.util.List;
import java.util.Set;

/**
 * @类名 ImportSubTaskService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/23 15:05
 * @版本 1.0
 */
public interface ImportSubTaskService {

    void addImportSubTask(ImportSubTask importSubTask) throws GlobalException;

    List<ImportSubTask> querySubtasksByTaskStatus(String taskStatus);

    Set<Long> querySubtaskIds(Set<String> taskIds);

    void deleteSubTask(Long[] toArray) throws GlobalException;
}
