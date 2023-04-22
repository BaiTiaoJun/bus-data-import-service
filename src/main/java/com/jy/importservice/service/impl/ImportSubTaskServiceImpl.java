package com.jy.importservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.util.PageUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportSubTask;
import com.jy.importservice.mapper.ImportSubTaskMapper;
import com.jy.importservice.service.ImportSubTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
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
public class ImportSubTaskServiceImpl implements ImportSubTaskService {

    @Resource
    private ImportSubTaskMapper importSubTaskMapper;

    @Override
    public ReturnVo queryByPage(Long pageNo, Long pageSize, String taskStatus) {
        pageNo = (pageNo - 1) * pageSize;
        List<ImportSubTask> importSubTasks = importSubTaskMapper.selectByPage(pageNo, pageSize, taskStatus);
        long totalSize = importSubTaskMapper.selectTotalSize();
        long totalPage = PageUtil.getTotalPage(totalSize, pageSize);
        return ReturnVo.ok().put(importSubTasks).put("totalSize", totalSize).put("totalPage", totalPage);
    }

    @Override
    public ImportSubTask queryDetails(Long id, String tableName, String taskStatus) {
        return importSubTaskMapper.selectDetails(id, tableName, taskStatus);
    }

    @Override
    public void deleteSubTask(Long... ids) throws GlobalException {
        Integer lockStatus = importSubTaskMapper.lockResource(ids);
        if (ObjectUtil.isNull(lockStatus) || lockStatus.equals(0)) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        int res = importSubTaskMapper.deleteByPrimaryKey(ids);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.DELETE_FAIL);
        }
    }

    @Override
    public void addImportSubTask(ImportSubTask importSubTask) throws GlobalException {
        int res = importSubTaskMapper.insertSelective(importSubTask);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.ADD_FAIL);
        }
    }

    @Override
    public Set<Long> querySubtaskIds(Set<String> taskIds) {
        return importSubTaskMapper.selectSubStaskIds(taskIds);
    }
}
