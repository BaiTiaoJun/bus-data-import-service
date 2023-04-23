package com.jy.importservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.entity.ImportSubTask;
import com.jy.importservice.mapper.ImportSubTaskMapper;
import com.jy.importservice.service.ImportSubTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @类名 ImportSubTaskServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/23 15:09
 * @版本 1.0
 */
@Service
public class ImportSubTaskServiceImpl implements ImportSubTaskService {

    @Resource
    private ImportSubTaskMapper importSubTaskMapper;

    @Override
    public void addImportSubTask(ImportSubTask importSubTask) throws GlobalException {
        int res = importSubTaskMapper.insertSelective(importSubTask);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.ADD_FAIL);
        }
    }

    @Override
    public List<ImportSubTask> querySubtasksByTaskStatus(String taskStatus) {
        return importSubTaskMapper.selectSubtasksByTaskStatus(taskStatus);
    }

    @Override
    public Set<Long> querySubtaskIds(Set<String> taskIds) {
        return importSubTaskMapper.selectSubStaskIds(taskIds);
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


}
