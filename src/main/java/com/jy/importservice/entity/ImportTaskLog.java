package com.jy.importservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportTaskLog {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String taskLogId;

    private String taskId;

    private String ruleTableId;

    private String sqlldrInfoId;

    private String subtaskId;

    private String filePath;

    private String fileName;

    private String fileSize;

    private String fileRecordRows;

    private Date startTime;

    private Date endTime;

    private Date circleTime;

    private Date updateTime;

    private Date deleteTime;

    private String errorMsg;

    private String tableName;

    private String sqlldrInfoName;

    private String taskStatus;

    private String tnsUrl;

    private String ctlContext;
}