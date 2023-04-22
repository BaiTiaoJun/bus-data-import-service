package com.jy.importservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.dto.ReturnDto;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.util.StringUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportDirection;
import com.jy.importservice.properties.HttpResourceProperty;
import com.jy.importservice.service.ImportDirectionService;
import com.mysql.cj.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @类名 ImportDicController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 16:15
 * @版本 1.0
 */
@RestController
@RequestMapping("/dic")
public class ImportDicController {
    
    private final ImportDirectionService importDirectionService;

    private final RestTemplate restTemplate;

    private final HttpResourceProperty resourceProperty;

    public ImportDicController(ImportDirectionService importDirectionService, RestTemplate restTemplate, HttpResourceProperty resourceProperty) {
        this.importDirectionService = importDirectionService;
        this.restTemplate = restTemplate;
        this.resourceProperty = resourceProperty;
    }

    @GetMapping("/query-by-type")
    public ReturnVo queryDicData(@RequestParam String type) {
        List<ImportDirection> importDirections = importDirectionService.queryDicData(type);
        return ReturnVo.ok().put(importDirections);
    }

    @GetMapping("/query-city-code")
    public ReturnVo queryCityCodes(@RequestParam String type) throws GlobalException {
        if (StringUtils.isNullOrEmpty(type)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        String[] uri = resourceProperty.getUris();
        if (uri == null || uri.length == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        String url = StringUtil.concat(new StringBuffer(), uri[4], "?type={cityCode}");

        Object obj = restTemplate.getForObject(url, Object.class, type);

        ReturnDto returnDto = JSONObject.parseObject(JSONObject.toJSONString(obj), ReturnDto.class);

        return ReturnVo.ok().put(returnDto.getData());
    }
}
