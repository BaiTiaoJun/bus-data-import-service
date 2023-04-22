package com.jy.importservice.service;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportSubTask;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @类名 TaskLogService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 18:10
 * @版本 1.0
 */
public interface ImportSubTaskService {

    ReturnVo queryByPage(Long pageNo, Long pageSize, String taskStatus);

    ImportSubTask queryDetails(Long id, String tableName, String taskStatus);

    void deleteSubTask(Long... ids) throws GlobalException;

    void addImportSubTask(ImportSubTask importSubTask) throws GlobalException;

    Set<Long> querySubtaskIds(@Param("taskIds") Set<String> taskIds);
}
