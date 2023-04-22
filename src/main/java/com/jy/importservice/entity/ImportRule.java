package com.jy.importservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportRule {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String ruleId;

    private String dataSourceId;

    private String sqlldrInfoId;

    private String ruleName;

    private String seperator;

    private String targetDatabase;

    private String sourceDatabase;

    private String loadType;

    private Date updateTime;

    private String sqlldrInfoName;

    private String ruleTableId;
}