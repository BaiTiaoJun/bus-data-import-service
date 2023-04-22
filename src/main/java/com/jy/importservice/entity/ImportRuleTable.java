package com.jy.importservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportRuleTable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String ruleTableId;

    private String ruleId;

    private String dataSourceId;

    private String targetTableName;

    private String targetSchema;

    private String sourceTableName;

    private String sourceSchema;

    private String isDynamicTable;

    private String dynamicTableRule;

    private Integer dynamicTableRuleValue;

    private Date updateTime;

    private String cityCode;

    private String targetColumnList;

    protected List<ImportColumn> importColumns;

    private String sourceDatabase;

    private String tnsUrl;

    private String sqlldrInfoName;
}