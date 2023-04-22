package com.jy.importservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.dto.ImportStoreDto;
import com.jy.importservice.common.dto.ReturnDto;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.properties.HttpResourceProperty;
import com.jy.importservice.service.ImportStoreService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @类名 ImportStoreServiceImpl
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 15:52
 * @版本 1.0
 */
@Service
public class ImportStoreServiceImpl implements ImportStoreService {

    @Resource
    private HttpResourceProperty resourceProperty;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<ImportStoreDto> queryAllNames() throws GlobalException {
        String[] uris = resourceProperty.getUris();
        if (ObjectUtil.isNull(uris) && uris.length == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        Object obj = restTemplate.getForObject(uris[5], Object.class);

        ReturnDto returnDto = JSONObject.parseObject(JSONObject.toJSONString(obj), ReturnDto.class);
        return JSONObject.parseArray(JSONObject.toJSONString(returnDto.getData()), ImportStoreDto.class);
    }
}
