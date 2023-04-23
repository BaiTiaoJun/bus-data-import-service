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
public class ImportTask {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String taskId;

    private String ruleId;

    private String storeId;

    private String dataSourceId;

    private String sqlldrInfoId;

    private String taskName;

    private String businessName;

    private String dataSourceName;

    private String taskStatus;

    private String taskType;

    private String cornTab;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date nextStartTime;

    private Date latestStartTime;

    private Date updateTime;

    private Integer dataIntervalValue;

    private String dataIntervalUnit;

    private String storeName;

    private String ruleName;

    private String storeType;
}