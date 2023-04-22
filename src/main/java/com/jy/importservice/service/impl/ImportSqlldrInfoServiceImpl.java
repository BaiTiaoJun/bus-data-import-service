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
import com.jy.importservice.entity.ImportSqlldrInfo;
import com.jy.importservice.mapper.ImportSqlldrInfoMapper;
import com.jy.importservice.service.ImportSqlldrInfoService;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @类名 ImportDataSourceServiceImpl
 * @描述 sqlldrInfo模块操作业务
 * @作者 白条君
 * @创建日期 2023/4/12 20:02
 * @版本 1.0
 */
@Service
public class ImportSqlldrInfoServiceImpl implements ImportSqlldrInfoService {

    @Resource
    private ImportSqlldrInfoMapper importSqlldrInfoMapper;

    @Override
    public ReturnVo queryByPage(Long pageNo, Long pageSize, String sqlldrInfoName) {
        pageNo = (pageNo - 1) * pageSize;
        List<ImportSqlldrInfo> importSqlldrInfos = importSqlldrInfoMapper.selectByPage(pageNo, pageSize, sqlldrInfoName);
        long totalSize = importSqlldrInfoMapper.selectTotalSize();
        long totalPage = PageUtil.getTotalPage(totalSize, pageSize);
        return ReturnVo.ok().put(importSqlldrInfos).put("totalSize", totalSize).put("totalPage", totalPage);
    }

    @Override
    public List<ImportSqlldrInfo> queryAllNames() {
        return importSqlldrInfoMapper.selectAllNames();
    }

    @Override
    public void addSqlldrInfo(ImportSqlldrInfo importSqlldrInfo) throws GlobalException {
        String sqlldrUrl = importSqlldrInfo.getSqlldrUrl();
        String tnsUrl = importSqlldrInfo.getTnsUrl();
        String sqlldrInfoName = importSqlldrInfo.getSqlldrInfoName();
        if (StringUtils.isNullOrEmpty(sqlldrUrl) || StringUtils.isNullOrEmpty(tnsUrl) || StringUtils.isNullOrEmpty(sqlldrInfoName)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        String sqlldrId = StringUtil.concat(new StringBuffer(), PrefixConstants.SQLLDR_PREFIX, "_", String.valueOf(SnowFlakeUtil.getNextId()));
        importSqlldrInfo.setSqlldrInfoId(sqlldrId);

        int res = importSqlldrInfoMapper.insertSelective(importSqlldrInfo);
        if (res == 0) {
            throw new GlobalException(ExpConstants.ADD_FAIL);
        }
    }

    @Transactional
    @Override
    public void modifySqlldrInfo(ImportSqlldrInfo importSqlldrInfo) throws GlobalException {
        Long id = importSqlldrInfo.getId();
        if (id == null) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        importSqlldrInfo.setUpdateTime(DateTimeUtil.getNow());

        this.lockResource(id);

        int res = importSqlldrInfoMapper.updateByPrimaryKeySelective(importSqlldrInfo);
        if (res == 0) {
            throw new GlobalException(ExpConstants.MODIFY_FAIL);
        }
    }

    @Transactional
    @Override
    public void deleteSqlldrInfo(Long id) throws GlobalException {
        this.lockResource(id);

        int res = importSqlldrInfoMapper.deleteByPrimaryKey(id);
        if (res == 0) {
            throw new GlobalException(ExpConstants.DELETE_FAIL);
        }
    }

    private void lockResource(Long id) throws GlobalException {
        Integer lockStatus = importSqlldrInfoMapper.lockResource(id);
        if (lockStatus == null) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.LOCK_FAIL);
        }
    }
}
