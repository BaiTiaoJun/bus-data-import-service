package com.jy.importservice.handler;

import com.jy.importservice.common.constants.PrefixConstants;
import com.jy.importservice.common.enums.TaskStatusEnum;
import com.jy.importservice.common.util.BeanFactoryUtil;
import com.jy.importservice.common.util.DateTimeUtil;
import com.jy.importservice.common.util.SnowFlakeUtil;
import com.jy.importservice.common.util.StringUtil;
import com.jy.importservice.entity.ImportRuleTable;
import com.jy.importservice.entity.ImportSubTask;
import com.jy.importservice.entity.ImportTask;
import com.jy.importservice.entity.ImportTaskLog;
import com.jy.importservice.properties.CtlContentProperty;
import com.jy.importservice.service.*;
import lombok.SneakyThrows;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * @类名 CornTabHandler
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 17:59
 * @版本 1.0
 */
public class FixedTimeTaskHandler extends QuartzJobBean {

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) {
        ImportRuleService importRuleService = BeanFactoryUtil.getBean(ImportRuleService.class);
        ImportTaskService importTaskService = BeanFactoryUtil.getBean(ImportTaskService.class);
        ImportTaskLogService importTaskLogService = BeanFactoryUtil.getBean(ImportTaskLogService.class);
        ImportSubTaskService importSubTaskService = BeanFactoryUtil.getBean(ImportSubTaskService.class);
        CtlContentProperty ctlContentProperty = BeanFactoryUtil.getBean(CtlContentProperty.class);

        //获取任务的id
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String taskId = (String) jobDataMap.get("id");

        // 根据id获取相关的任务信息
        ImportTask importTask = importTaskService.queryByTaskId(taskId);

        //更新任务执行状态
//        String taskStatus = String.valueOf(TaskStatusEnum.RUNNING.getCode());
//        importTaskService.modifyTask(ImportTask.builder().id(id).taskStatus(taskStatus).build(), null);

        //获取当前启动时间
        Date nextStartTime = importTask.getNextStartTime();
        //启动时间到数据周期的间隔值
        Integer dataIntervalValue = importTask.getDataIntervalValue();
        //数据周期单位
        String dataIntervalUnit = importTask.getDataIntervalUnit();

        //计算数据周期，数据周期=启动时间-数据周期间隔
        Date dataCircleTime = DateTimeUtil.updateDataCircleTime(dataIntervalUnit, -dataIntervalValue, nextStartTime);
        //格式化数据周期
        String dataCircle = DateTimeUtil.formatDataCircle(dataCircleTime, dataIntervalUnit);

        String ruleId = importTask.getRuleId();

        //创建导入子任务
        ImportRuleTableService importRuleTableService = BeanFactoryUtil.getBean(ImportRuleTableService.class);
        List<ImportRuleTable> importRuleTables = importRuleTableService.queryByRuleId(ruleId);
        ImportTaskLog importTaskLog;
        for (ImportRuleTable importRuleTable : importRuleTables) {
            String sourceTableName = importRuleTable.getSourceTableName();

            //获取sqlldrInfoId
            String sqlldrInfoId = importRuleService.selectSqlldrInfoIdByRuleId(ruleId).getSqlldrInfoId();

            //创建子任务
            ImportSubTask importSubTask = new ImportSubTask();
            String subtaskId = StringUtil.concat(new StringBuffer(), PrefixConstants.SUBTASK_PREFIX, "_", String.valueOf(SnowFlakeUtil.getNextId()));
            importSubTask.setSubtaskId(subtaskId);
            importSubTask.setSqlldrInfoId(sqlldrInfoId);
            importSubTask.setTaskId(taskId);
            //创建并添加文件名称
            String sourceDatabase = importRuleTable.getSourceDatabase();
            String sourceSchema = importRuleTable.getSourceSchema();
            String cityCode = importRuleTable.getCityCode();
            String fileName = StringUtil.concat(new StringBuffer(),
                    sourceDatabase, "_", sourceSchema, "_", sourceTableName, "_", cityCode, "_", "[", dataCircle, "]", ".verf");
            importSubTask.setFileName(fileName);
            //创建并添加ctl内容
            String targetColumnList = importRuleTableService.queryTargetColumnListByRuleTableId(importRuleTable.getRuleTableId());
            String content = ctlContentProperty.getContent();
            StringBuilder ctlFileContent = new StringBuilder(content);
            ctlFileContent.insert(content.indexOf('(') + 1, "\n" + targetColumnList);
            importSubTask.setCtlContext(ctlFileContent.toString());
            //添加子任务
            importSubTaskService.addImportSubTask(importSubTask);

            //创建子任务日志
            importTaskLog = new ImportTaskLog();
            importTaskLog.setTaskLogId(StringUtil.concat(new StringBuffer(), PrefixConstants.TASK_LOG_PREFIX, "_", String.valueOf(SnowFlakeUtil.getNextId())));
            importTaskLog.setTaskId(taskId);
            importTaskLog.setRuleTableId(importRuleTable.getRuleTableId());
            importTaskLog.setSqlldrInfoId(sqlldrInfoId);
            importTaskLog.setCircleTime(dataCircleTime);
            importTaskLog.setTableName(sourceTableName);
            importTaskLog.setSqlldrInfoName(importRuleTable.getSqlldrInfoName());
            importTaskLog.setTaskStatus(String.valueOf(TaskStatusEnum.NOT_START.getCode()));
            importTaskLog.setSubtaskId(subtaskId);
            importTaskLogService.addImportTaskLog(importTaskLog);
        }

        //更新最新执行时间和下一次的执行时间
        ImportTask task = new ImportTask();
        task.setId(importTask.getId());
        task.setLatestStartTime(nextStartTime);
        nextStartTime = context.getTrigger().getNextFireTime();
        task.setNextStartTime(nextStartTime);
        task.setUpdateTime(new Date());
        importTaskService.modifyTask(task, null);
    }
}
