package com.jy.importservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.constants.PrefixConstants;
import com.jy.importservice.common.dto.ImportDataSourceNamesDto;
import com.jy.importservice.common.dto.ImportStoreDto;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.enums.TaskStatusEnum;
import com.jy.importservice.common.enums.TaskTypeEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.util.DateTimeUtil;
import com.jy.importservice.common.util.PageUtil;
import com.jy.importservice.common.util.SnowFlakeUtil;
import com.jy.importservice.common.util.StringUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportRule;
import com.jy.importservice.entity.ImportTask;
import com.jy.importservice.handler.FixedTimeTaskHandler;
import com.jy.importservice.mapper.ImportTaskMapper;
import com.jy.importservice.service.*;
import com.mysql.cj.util.StringUtils;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @类名 ImportTaskServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/18 19:07
 * @版本 1.0
 */
@Service
public class ImportTaskServiceImpl implements ImportTaskService {

    @Resource
    private ImportTaskMapper importTaskMapper;

    @Resource
    private ImportDataSourceService importDataSourceService;

    @Resource
    private ImportStoreService importStoreService;

    @Resource
    private ThreadPoolExecutor executor;

    @Resource
    private Scheduler scheduler;

    @Resource
    private ImportRuleService importRuleService;

    @Resource
    private ImportTaskLogService importTaskLogService;

    @Resource
    private ImportSubTaskService importSubTaskService;

    @Override
    public ReturnVo queryByPage(Long pageNo, Long pageSize, String taskName, String taskType, String dataSource, String storeName, String taskStatus) throws GlobalException, InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);

        /**
         * 分页获取任务信息
         */
        AtomicReference<List<ImportTask>> importTasksReference = new AtomicReference<>();
        executor.execute(() -> {
            Long finalPageNo = (pageNo - 1) * pageSize;
            try {
                importTasksReference.set(importTaskMapper.selectByPage(finalPageNo, pageSize, taskName, taskType, taskStatus));
            } finally {
                latch.countDown();
            }
        });

        /**
         * 获取存储信息到任务中
         */
        AtomicReference<List<ImportStoreDto>> storeNameDtoReference = new AtomicReference<>();
        executor.execute(() -> {
            try {
                storeNameDtoReference.set(importStoreService.queryAllNames());
            } catch (GlobalException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        /**
         * 获取数据源信息到任务中
         */
        AtomicReference<List<ImportDataSourceNamesDto>> dataSourceNamesDtoReference = new AtomicReference<>();
        executor.execute(() -> {
            try {
                dataSourceNamesDtoReference.set(importDataSourceService.queryDataSourceNames());
            } catch (GlobalException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        List<ImportTask> importTasks = importTasksReference.get();
        List<ImportStoreDto> storeNameDtos = storeNameDtoReference.get();
        List<ImportDataSourceNamesDto> dataSourceNamesDtos = dataSourceNamesDtoReference.get();

        for (ImportTask importTask : importTasks) {
            for (ImportStoreDto storeNameDto : storeNameDtos) {
                if (importTask.getStoreId().equals(storeNameDto.getStore_id())) {
                    importTask.setStoreName(storeNameDto.getStore_name());
                    importTask.setStoreType(storeNameDto.getStore_type());
                }
            }

            for (ImportDataSourceNamesDto dataSourceNameDto : dataSourceNamesDtos) {
                if (dataSourceNameDto.getData_source_id().equals(importTask.getDataSourceId())) {
                    importTask.setDataSourceName(dataSourceNameDto.getData_source_name());
                }
            }
            importTask.setStoreId(null);
            importTask.setDataSourceId(null);
        }

        Long totalSize = importTaskMapper.selectTotalSize();
        Long totalPage = PageUtil.getTotalPage(totalSize, pageSize);
        return ReturnVo.ok().put(importTasks).put("totalSize", totalSize).put("totalPage", totalPage);
    }

    @Override
    public void addTask(ImportTask importTask) throws GlobalException, SchedulerException {
        String taskName = importTask.getTaskName();
        String dataSourceId = importTask.getDataSourceId();
        String ruleId = importTask.getRuleId();
        String storeId = importTask.getStoreId();
        String taskType = importTask.getTaskType();

        if (StringUtils.isNullOrEmpty(taskName) || StringUtils.isNullOrEmpty(dataSourceId) ||
                StringUtils.isNullOrEmpty(storeId) || StringUtils.isNullOrEmpty(taskType) || StringUtils.isNullOrEmpty(ruleId)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        Date nextStartTime = null;
        String cornTab = null;
        if (Integer.valueOf(taskType).equals(TaskTypeEnum.AUTO_EXECUTE.getCode())) {
            Integer value = importTask.getDataIntervalValue();
            String unit = importTask.getDataIntervalUnit();
            nextStartTime = importTask.getNextStartTime();
            cornTab = importTask.getCornTab();
            if (ObjectUtil.isNull(value) || value.equals(0) ||
                    StringUtils.isNullOrEmpty(unit) || ObjectUtil.isNull(nextStartTime) || StringUtils.isNullOrEmpty(cornTab)) {
                throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
            }
        }

        String taskId = StringUtil.concat(new StringBuffer(), PrefixConstants.TASK_PREFIX, "_", SnowFlakeUtil.getNextId().toString());
        importTask.setTaskId(taskId);
        importTask.setTaskStatus(String.valueOf(TaskStatusEnum.NOT_START.getCode()));

        //添加sqlldrinfoid
        ImportRule importRule = importRuleService.selectSqlldrInfoIdByRuleId(ruleId);
        importTask.setSqlldrInfoId(importRule.getSqlldrInfoId());

        int res = importTaskMapper.insertSelective(importTask);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.ADD_FAIL);
        }

        //如果是自动执行类型的任务就创建新的任务实例和触发器并加入到调度器
        if (Integer.valueOf(taskType).equals(TaskTypeEnum.AUTO_EXECUTE.getCode())) {
            //创建任务实例
            JobDetail jobDetail = JobBuilder.newJob(FixedTimeTaskHandler.class).withIdentity(taskId).build();
            jobDetail.getJobDataMap().put("id", taskId);

            //通过指定开始时间和cron表达式,创建触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(taskId)
                    .startAt(nextStartTime)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cornTab)
                            //如果cron表达式指定的时间错过了当前的这个时间点就会忽略，等待下一次到达这个时间点执行
                            .withMisfireHandlingInstructionDoNothing()).build();

            //关联触发器和任务
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    @Override
    public ImportTask queryById(Long id) {
        return importTaskMapper.selectDetailsById(id);
    }

    @Override
    public void modifyTask(ImportTask importTask) throws GlobalException, SchedulerException {
        Long id = importTask.getId();
        String taskName = importTask.getTaskName();
        String dataSourceId = importTask.getDataSourceId();
        String ruleId = importTask.getRuleId();
        String storeId = importTask.getStoreId();
        String taskType = importTask.getTaskType();
        if (StringUtils.isNullOrEmpty(taskName) || StringUtils.isNullOrEmpty(dataSourceId) ||
                StringUtils.isNullOrEmpty(storeId) || StringUtils.isNullOrEmpty(taskType) ||
                StringUtils.isNullOrEmpty(ruleId) || ObjectUtil.isNull(id)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        Date nextStartTime = null;
        String cornTab = null;
        if (Integer.valueOf(taskType).equals(TaskTypeEnum.AUTO_EXECUTE.getCode())) {
            Integer value = importTask.getDataIntervalValue();
            String unit = importTask.getDataIntervalUnit();
            nextStartTime = importTask.getNextStartTime();
            cornTab = importTask.getCornTab();
            if (ObjectUtil.isNull(value) || value.equals(0) || StringUtils.isNullOrEmpty(unit) ||
                    ObjectUtil.isNull(nextStartTime) || StringUtils.isNullOrEmpty(cornTab)) {
                throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
            }
        }

        String taskId = this.getTaskIds(id).iterator().next();

        //修改任务
        this.modifyTask(importTask, id);

        if (Integer.valueOf(taskType).equals(TaskTypeEnum.AUTO_EXECUTE.getCode())) {
            //重新修改定时任务
            TriggerKey triggerKey = TriggerKey.triggerKey(taskId);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            cronTrigger = cronTrigger.getTriggerBuilder()
                    .withIdentity(taskId)
                    .startAt(nextStartTime)
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule(cornTab)
                            .withMisfireHandlingInstructionDoNothing())
                    .build();
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        }
    }

    @Transactional
    public void modifyTask(ImportTask importTask, Long id) throws GlobalException {
        //锁定修改资源
        this.lockResource(id);

        importTask.setUpdateTime(DateTimeUtil.getNow());

        //更新操作
        int res = importTaskMapper.updateByPrimaryKeySelective(importTask);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.MODIFY_FAIL);
        }
    }

    @Override
    public void deleteTask(Long[] ids, String taskType) throws GlobalException, SchedulerException {
        //获取所有id对应的taskid
        Set<String> taskIds = this.getTaskIds(ids);

        this.deleteTask(ids, 0L);

        if (Integer.valueOf(taskType).equals(TaskTypeEnum.AUTO_EXECUTE.getCode())) {
            //删除定时任务
            for (String taskId : taskIds) {
                scheduler.deleteJob(JobKey.jobKey(taskId));
            }
        }
    }

    @Transactional
    public void deleteTask(Long[] ids, Long... ignore) throws GlobalException {
        //获取taskid
        Set<String> taskIds = importTaskMapper.selectTaskIds(ids);

        //获取subtask的id
        Set<Long> subtaskIds =  importSubTaskService.querySubtaskIds(taskIds);
        //删除子任务
        if (ObjectUtil.isNotNull(subtaskIds) && subtaskIds.size() > 0) {
            importSubTaskService.deleteSubTask(subtaskIds.toArray(new Long[0]));
        }

        //获取tasklog的id
        Set<Long> taskLogIds = importTaskLogService.queryTaskLogIds(taskIds);
        //删除子任务日志
        if (ObjectUtil.isNotNull(taskLogIds) && taskLogIds.size() > 0) {
            importTaskLogService.deleteTaskLog(taskLogIds.toArray(new Long[0]));
        }

        //锁定资源
        Long lockStatus = importTaskMapper.lockResources(ids);
        if (ObjectUtil.isNull(lockStatus) || lockStatus == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.LOCK_FAIL);
        }

        //删除定时任务
        Long res = importTaskMapper.deleteByIds(ids);
        if (ObjectUtil.isNull(res) || res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.DELETE_FAIL);
        }
    }

    @Override
    public void pauseTask(Long id) throws GlobalException, SchedulerException {
        Set<String> taskIds = this.getTaskIds(id);
        scheduler.pauseJob(JobKey.jobKey(taskIds.iterator().next()));
    }

    @Override
    public void resumeTask(Long id) throws GlobalException, SchedulerException {
        Set<String> taskIds = this.getTaskIds(id);
        scheduler.resumeJob(JobKey.jobKey(taskIds.iterator().next()));
    }

    @Override
    public ImportTask queryByTaskId(String taskId) {
        return importTaskMapper.selectByTaskId(taskId);
    }

    @Override
    public void modifyTask(ImportTask importTask, Object... ignore) throws GlobalException {
        this.lockResource(importTask.getId());
        int res = importTaskMapper.updateByPrimaryKeySelective(importTask);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.MODIFY_FAIL);
        }
    }

    private Set<String> getTaskIds(Long... ids) throws GlobalException {
        Set<String> idList = importTaskMapper.selectTaskIds(ids);
        if (ObjectUtil.isNull(idList) || idList.isEmpty()) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.NULL_OBJECT);
        }
        return idList;
    }

    private void lockResource(Long id) throws GlobalException {
        Integer lockStatus = importTaskMapper.lockResource(id);
        if (lockStatus == null) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.LOCK_FAIL);
        }
    }
}
