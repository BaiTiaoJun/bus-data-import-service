package com.jy.importservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.dto.ImportDataSourceDto;
import com.jy.importservice.common.dto.ImportDataSourceNamesDto;
import com.jy.importservice.common.dto.ReturnDto;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.properties.HttpResourceProperty;
import com.jy.importservice.service.ImportDataSourceService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @类名 ImportDataSourceServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 15:35
 * @版本 1.0
 */
@Service
public class ImportDataSourceServiceImpl implements ImportDataSourceService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HttpResourceProperty resourceProperty;

    @Override
    public List<ImportDataSourceNamesDto> queryDataSourceNames() throws GlobalException {
        String[] uri = resourceProperty.getUris();
        if (uri == null || uri.length == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        Object obj = restTemplate.getForObject(uri[0], Object.class);
        if (ObjectUtil.isNull(obj)) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.NULL_OBJECT);
        }

        ReturnDto returnDto = JSONObject.parseObject(JSONObject.toJSONString(obj), ReturnDto.class);
        return JSONObject.parseArray(JSONObject.toJSONString(returnDto.getData()), ImportDataSourceNamesDto.class);
    }
}
