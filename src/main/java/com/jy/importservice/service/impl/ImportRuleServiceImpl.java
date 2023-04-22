package com.jy.importservice.service.impl;

import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.constants.PrefixConstants;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.util.DateTimeUtil;
import com.jy.importservice.common.util.PageUtil;
import com.jy.importservice.common.util.SnowFlakeUtil;
import com.jy.importservice.common.util.StringUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportRule;
import com.jy.importservice.mapper.ImportRuleMapper;
import com.jy.importservice.service.ImportRuleService;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @类名 ImportRuleServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 10:13
 * @版本 1.0
 */
@Service
public class ImportRuleServiceImpl implements ImportRuleService {

    @Resource
    private ImportRuleMapper importRuleMapper;

    @Override
    public void addImportRule(ImportRule importRule) throws GlobalException {
        if (StringUtils.isNullOrEmpty(importRule.getRuleName()) || StringUtils.isNullOrEmpty(importRule.getSourceDatabase()) ||
                StringUtils.isNullOrEmpty(importRule.getTargetDatabase()) || StringUtils.isNullOrEmpty(importRule.getSeperator())
                || StringUtils.isNullOrEmpty(importRule.getLoadType()) || StringUtils.isNullOrEmpty(importRule.getSqlldrInfoId())) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        String ruleId = StringUtil.concat(new StringBuffer(),PrefixConstants.RULE_PREFIX, "_", String.valueOf(SnowFlakeUtil.getNextId()));
        importRule.setRuleId(ruleId);

        int res = importRuleMapper.insertSelective(importRule);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.ADD_FAIL);
        }
    }

    @Override
    public ReturnVo queryByPage(Long pageNo, Long pageSize, String ruleName, String sourceDatabase, String targetDatabase) {
        pageNo = (pageNo - 1) * pageSize;
        List<ImportRule> importRules = importRuleMapper.selectByPage(pageNo, pageSize, ruleName, sourceDatabase, targetDatabase);
        long totalSize = importRuleMapper.selectTotalSize();
        long totalPage = PageUtil.getTotalPage(totalSize, pageSize);
        return ReturnVo.ok().put(importRules).put("totalSize", totalSize).put("totalPage", totalPage);
    }

    @Override
    public ImportRule queryById(Long id) {
        return importRuleMapper.selectById(id);
    }

    @Transactional
    @Override
    public void modifyRule(ImportRule importRule) throws GlobalException {
        Long id = importRule.getId();
        String ruleName = importRule.getRuleName();
        String seperator = importRule.getSeperator();
        String loadType = importRule.getLoadType();

        if (StringUtils.isNullOrEmpty(ruleName) || StringUtils.isNullOrEmpty(seperator) || StringUtils.isNullOrEmpty(loadType)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        //资源锁定
        this.lockResource(id);

        //修改资源
        importRule.setUpdateTime(DateTimeUtil.getNow());
        int res = importRuleMapper.updateByPrimaryKeySelective(importRule);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.MODIFY_FAIL);
        }
    }

    @Transactional
    @Override
    public void deleteRule(Long id) throws GlobalException {
        this.lockResource(id);

        int res = importRuleMapper.deleteByPrimaryKey(id);
        if (res == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.DELETE_FAIL);
        }
    }

    @Override
    public List<ImportRule> queryAllNames() {
        return importRuleMapper.selectAllNames();
    }

    @Override
    public ImportRule selectSqlldrInfoIdByRuleId(String ruleId) {
        return importRuleMapper.selectSqlldrInfoIdByRuleId(ruleId);
    }

    private void lockResource(Long id) throws GlobalException {
        Integer lockStatus = importRuleMapper.lockResource(id);
        if (lockStatus == null) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.LOCK_FAIL);
        }
    }
}
