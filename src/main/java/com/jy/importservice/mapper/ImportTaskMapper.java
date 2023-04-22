package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ImportTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImportTask record);

    int insertSelective(ImportTask record);

    ImportTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportTask record);

    int updateByPrimaryKey(ImportTask record);

    List<ImportTask> selectByPage(@Param("pageNo") Long pageNo, @Param("pageSize") Long pageSize, @Param("taskName") String taskName
            , @Param("taskType") String taskType, @Param("taskStatus") String taskStatus);

    Long selectTotalSize();

    ImportTask selectDetailsById(Long id);

    Integer lockResource(Long id);

    Long lockResources(@Param("ids") Long[] ids);

    Long deleteByIds(@Param("ids") Long[] ids);

    Set<String> selectTaskIds(@Param("ids") Long[] ids);

    ImportTask selectByTaskId(String taskId);
}