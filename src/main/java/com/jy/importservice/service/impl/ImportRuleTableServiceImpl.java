package com.jy.importservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.constants.PrefixConstants;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.util.DateTimeUtil;
import com.jy.importservice.common.util.PageUtil;
import com.jy.importservice.common.util.SnowFlakeUtil;
import com.jy.importservice.common.util.StringUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportColumn;
import com.jy.importservice.entity.ImportRuleTable;
import com.jy.importservice.mapper.ImportRuleTableMapper;
import com.jy.importservice.service.ImportRuleTableService;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @类名 ImportTableServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 18:42
 * @版本 1.0
 */
@Service
public class ImportRuleTableServiceImpl implements ImportRuleTableService {

    @Resource
    private ImportRuleTableMapper importRuleTableMapper;

    @Override
    public ReturnVo queryByPage(Long pageNo, Long pageSize, String sourceSchema, String sourceTableName, String targetSchema, String targetTableName) {
        pageNo = (pageNo - 1) * pageSize;
        List<ImportRuleTable> importTables = importRuleTableMapper.selectTableByPage(pageNo, pageSize, sourceSchema, sourceTableName, targetSchema, targetTableName);
        long totalSize = importRuleTableMapper.selectTableTotalSize();
        long totalPage = PageUtil.getTotalPage(totalSize, pageSize);
        return ReturnVo.ok().put(importTables).put("totalSize", totalSize).put("totalPage", totalPage);
    }

    @Override
    public ImportRuleTable queryById(Long id) {
        ImportRuleTable importRuleTable = importRuleTableMapper.selectByPrimaryKey(id);

        /**
         * 根据规则把字符串类型的字段列表为list中的字段
         */
        String targetColumnList = importRuleTable.getTargetColumnList();
        String[] columns = targetColumnList.split(",\n");

        List<ImportColumn> importColumns = new ArrayList<>();
        for (String column : columns) {
            ImportColumn importColumn = new ImportColumn();
            String[] columnMetas = column.split("\\s+");
            importColumn.setColumn(columnMetas[0]);
            importColumn.setColumnType(columnMetas[1]);
            int length = columnMetas.length;
            if (length == 3) {
                String columnType = columnMetas[2];
                importColumn.setColumnFormat(columnType.substring(1, columnType.length() - 1));
            } else if (length == 4) {
                String columnType = columnMetas[2] + columnMetas[3];
                importColumn.setColumnFormat(columnType.substring(1, columnType.length() - 1));
            }
            importColumns.add(importColumn);
        }

        importRuleTable.setImportColumns(importColumns);
        importRuleTable.setTargetColumnList(null);

        return importRuleTable;
    }

    @Override
    public List<ImportRuleTable> queryByRuleId(String ruleId) {
        return importRuleTableMapper.selectByRuleId(ruleId);
    }

    @Override
    public String queryTargetColumnListByRuleTableId(String ruleTableId) {
        return importRuleTableMapper.selectTargetColumnListByRuleTableId(ruleTableId);
    }

    @Override
    public void addRuleTable(ImportRuleTable importRuleTable) throws GlobalException {
        String isDynamicTable = importRuleTable.getIsDynamicTable();
        List<ImportColumn> importColumns = importRuleTable.getImportColumns();

        if (StringUtils.isNullOrEmpty(importRuleTable.getSourceSchema()) || StringUtils.isNullOrEmpty(importRuleTable.getSourceTableName()) ||
        StringUtils.isNullOrEmpty(importRuleTable.getTargetSchema()) || StringUtils.isNullOrEmpty(importRuleTable.getTargetTableName()) ||
        ObjectUtil.isNull(importColumns) || StringUtils.isNullOrEmpty(isDynamicTable)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        if (isDynamicTable.equals("0") && StringUtils.isNullOrEmpty(importRuleTable.getDynamicTableRule())) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        if (!StringUtils.isNullOrEmpty(importRuleTable.getDynamicTableRule()) && importRuleTable.getDynamicTableRuleValue() == null) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        /**
         * 集合中的字段列表转为字符串
         */
        String columns = this.listConvertString(importColumns);

        importRuleTable.setTargetColumnList(columns.substring(0, columns.length() - 2));
        String ruleTableId = StringUtil.concat(new StringBuffer(), PrefixConstants.RULE_TABLE_PREFIX, "_", String.valueOf(SnowFlakeUtil.getNextId()));
        importRuleTable.setRuleTableId(ruleTableId);

        /**
         * 添加到规则记录中
         */
        int res = importRuleTableMapper.insertSelective(importRuleTable);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.ADD_FAIL);
        }
    }

    @Transactional
    @Override
    public void modifyRuleTable(ImportRuleTable importRuleTable) throws GlobalException {
        String isDynamicTable = importRuleTable.getIsDynamicTable();
        List<ImportColumn> importColumns = importRuleTable.getImportColumns();
        Long id = importRuleTable.getId();

        if (StringUtils.isNullOrEmpty(importRuleTable.getSourceSchema()) || StringUtils.isNullOrEmpty(importRuleTable.getSourceTableName()) ||
                StringUtils.isNullOrEmpty(importRuleTable.getTargetSchema()) || StringUtils.isNullOrEmpty(importRuleTable.getTargetTableName()) ||
                ObjectUtil.isNull(importColumns) || StringUtils.isNullOrEmpty(isDynamicTable) || id == null) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        if (isDynamicTable.equals("0") && StringUtils.isNullOrEmpty(importRuleTable.getDynamicTableRule())) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        if (!StringUtils.isNullOrEmpty(importRuleTable.getDynamicTableRule()) && importRuleTable.getDynamicTableRuleValue() == null) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        importRuleTable.setUpdateTime(DateTimeUtil.getNow());

        /**
         * 集合中的字段列表转为字符串
         */
        String columns = this.listConvertString(importColumns);
        importRuleTable.setTargetColumnList(columns.substring(0, columns.length() - 2));

        /**
         * 锁定更新资源
         */
        this.lockResource(id);

        /**
         * 更新到规则记录中
         */
        int res = importRuleTableMapper.updateByPrimaryKeySelective(importRuleTable);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.MODIFY_FAIL);
        }
    }

    @Transactional
    @Override
    public void deleteRuleTable(Long id) throws GlobalException {
        this.lockResource(id);

        int res = importRuleTableMapper.deleteByPrimaryKey(id);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.DELETE_FAIL);
        }
    }

    private void lockResource(Long id) throws GlobalException {
        Integer lockStatus = importRuleTableMapper.lockResource(id);
        if (lockStatus == null) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.LOCK_FAIL);
        }
    }

    /**
     * 集合中的字段列表转为字符串
     */
    private String listConvertString(List<ImportColumn> importColumns) throws GlobalException {
        String columns = null;
        StringBuffer buffer = new StringBuffer();
        for (ImportColumn importColumn : importColumns) {
            String column = importColumn.getColumn();
            String columnType = importColumn.getColumnType();
            String columnFormat = importColumn.getColumnFormat();

            if (!StringUtils.isNullOrEmpty(columnFormat)) {
                columns = StringUtil.concat(buffer, column, " ", columnType, " ", "\"", columnFormat, "\"", ",", "\n");
            } else {
                columns = StringUtil.concat(buffer, column, " ", columnType, ",", "\n");
            }
        }

        if (StringUtils.isNullOrEmpty(columns) || columns.length() == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.GENERATOR_COLUMNS_FAIL);
        }
        return columns;
    }
}
