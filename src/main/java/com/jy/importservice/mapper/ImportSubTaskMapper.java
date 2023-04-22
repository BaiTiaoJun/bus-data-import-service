package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportSubTask;
import com.jy.importservice.entity.ImportTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ImportSubTaskMapper {
    int deleteByPrimaryKey(@Param("ids") Long[] id);

    int insert(ImportSubTask record);

    int insertSelective(ImportSubTask record);

    ImportSubTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportSubTask record);

    int updateByPrimaryKey(ImportSubTask record);

    List<ImportSubTask> selectByPage(@Param("pageNo") Long pageNo, @Param("pageSize") Long pageSize, @Param("taskStatus") String taskStatus);

    long selectTotalSize();

    Integer lockResource(@Param("ids") Long[] ids);

    ImportSubTask selectDetails(@Param("id") Long id, @Param("tableName") String tableName, @Param("taskStatus") String taskStatus);

    Set<Long> selectSubStaskIds(@Param("taskIds") Set<String> taskIds);
}