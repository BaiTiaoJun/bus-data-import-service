package com.jy.importservice.mapper;

import com.jy.importservice.entity.ImportTaskLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ImportTaskLogMapper {
    int deleteByPrimaryKey(@Param("ids") Long[] ids);

    int insert(ImportTaskLog record);

    int insertSelective(ImportTaskLog record);

    ImportTaskLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImportTaskLog record);

    int updateByPrimaryKeyWithBLOBs(ImportTaskLog record);

    int updateByPrimaryKey(ImportTaskLog record);

    List<ImportTaskLog> selectByPage(@Param("pageNo") Long pageNo, @Param("pageSize") Long pageSize, @Param("taskStatus") String taskStatus);

    long selectTotalSize();

    ImportTaskLog selectDetails(@Param("id") Long id, @Param("tableName") String tableName, @Param("taskStatus") String taskStatus);

    Integer lockResource(@Param("ids") Long... ids);

    Set<Long> selectTaskLogIds(@Param("taskIds") Set<String> taskIds);
}