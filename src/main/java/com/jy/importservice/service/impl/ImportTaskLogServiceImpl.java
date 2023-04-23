package com.jy.importservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.util.PageUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportTaskLog;
import com.jy.importservice.mapper.ImportTaskLogMapper;
import com.jy.importservice.service.ImportTaskLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @类名 TaskLogServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 18:11
 * @版本 1.0
 */
@Service
public class ImportTaskLogServiceImpl implements ImportTaskLogService {

    @Resource
    private ImportTaskLogMapper importTaskLogMapper;

    @Override
    public ReturnVo queryByPage(Long pageNo, Long pageSize, String taskStatus) {
        pageNo = (pageNo - 1) * pageSize;
        List<ImportTaskLog> importTaskLogs = importTaskLogMapper.selectByPage(pageNo, pageSize, taskStatus);
        long totalSize = importTaskLogMapper.selectTotalSize();
        long totalPage = PageUtil.getTotalPage(totalSize, pageSize);
        return ReturnVo.ok().put(importTaskLogs).put("totalSize", totalSize).put("totalPage", totalPage);
    }

    @Override
    public ImportTaskLog queryDetails(Long id, String tableName, String taskStatus) {
        return importTaskLogMapper.selectDetails(id, tableName, taskStatus);
    }

    @Override
    public void addImportTaskLog(ImportTaskLog importTaskLog) throws GlobalException {
        int res = importTaskLogMapper.insertSelective(importTaskLog);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.ADD_FAIL);
        }
    }

    @Override
    public void deleteTaskLog(Long... ids) throws GlobalException {
        Integer lockStatus = importTaskLogMapper.lockResource(ids);
        if (ObjectUtil.isNull(lockStatus) || lockStatus.equals(0)) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        int res = importTaskLogMapper.deleteByPrimaryKey(ids);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.DELETE_FAIL);
        }
    }

    @Override
    public Set<Long> queryTaskLogIds(Set<String> taskIds) {
        return importTaskLogMapper.selectTaskLogIds(taskIds);
    }
}
