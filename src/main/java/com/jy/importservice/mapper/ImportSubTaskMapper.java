package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportSubTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ImportSubTaskMapper {
    int deleteByPrimaryKey(@Param("ids") Long... ids);

    int insert(ImportSubTask record);

    int insertSelective(ImportSubTask record);

    ImportSubTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportSubTask record);

    int updateByPrimaryKeyWithBLOBs(ImportSubTask record);

    int updateByPrimaryKey(ImportSubTask record);

    List<ImportSubTask> selectSubtasksByTaskStatus(String taskStatus);

    Set<Long> selectSubStaskIds(@Param("taskIds") Set<String> taskIds);

    Integer lockResource(@Param("ids") Long[] ids);
}