package com.jy.importservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportSubTask {
    private Long id;

    private String ruleTableId;

    private String taskId;

    private String sqlldrInfoId;

    private String taskLogId;

    private String filePath;

    private String fileName;

    private Long fileSize;

    private Long fileRecordRows;

    private Date startTime;

    private Date endTime;

    private Date deleteTime;

    private Date updateTime;

    private Date circleTime;

    private String errorMsg;

    private String ctlFileContent;

    private String tableName;

    private String sqlldrInfoName;

    private String taskStatus;
}