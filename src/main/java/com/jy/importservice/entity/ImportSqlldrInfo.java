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
public class ImportSqlldrInfo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String sqlldrInfoId;

    private String sqlldrInfoName;

    private String tnsUrl;

    private Date updateTime;

    private String sqlldrUrl;
}