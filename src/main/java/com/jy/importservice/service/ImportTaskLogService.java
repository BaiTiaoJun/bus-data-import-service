package com.jy.importservice.service;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportTaskLog;

import java.util.Set;

/**
 * @类名 TaskLogService
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 18:10
 * @版本 1.0
 */
public interface ImportTaskLogService {

    ReturnVo queryByPage(Long pageNo, Long pageSize, String taskStatus);

    ImportTaskLog queryDetails(Long id, String tableName, String taskStatus);

    void addImportTaskLog(ImportTaskLog importTaskLog) throws GlobalException;

    void deleteTaskLog(Long... ids) throws GlobalException;

    Set<Long> queryTaskLogIds(Set<String> taskIds);
}
