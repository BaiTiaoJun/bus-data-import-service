package com.jy.importservice.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.dto.ImportDataSourceDto;
import com.jy.importservice.common.dto.ImportDataSourceNamesDto;
import com.jy.importservice.common.dto.ReturnDto;
import com.jy.importservice.common.dto.RuleTblDto;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.util.StringUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.properties.HttpResourceProperty;
import com.jy.importservice.service.ImportDataSourceService;
import com.mysql.cj.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @类名 ImportDataSourceController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 13:12
 * @版本 1.0
 */
@RestController
@RequestMapping("/target-data-source")
public class ImportDataSourceController {

    @Resource
    private ImportDataSourceService importDataSourceService;

    private final RestTemplate restTemplate;

    private final HttpResourceProperty resourceProperty;

    public ImportDataSourceController(RestTemplate restTemplate, HttpResourceProperty resourceProperty) {
        this.restTemplate = restTemplate;
        this.resourceProperty = resourceProperty;
    }

    @GetMapping("/query-all-names")
    public ReturnVo queryDataSourceNames() throws GlobalException {
        List<ImportDataSourceNamesDto> namesDtos = importDataSourceService.queryDataSourceNames();
        return ReturnVo.ok().put(namesDtos);
    }

    @GetMapping("/query-schemas")
    public ReturnVo queryDataSourceSchemas(@RequestParam String dataSourceId) throws GlobalException {
        if (StringUtils.isNullOrEmpty(dataSourceId)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        String[] uri = resourceProperty.getUris();
        if (uri == null || uri.length == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        String url = StringUtil.concat(new StringBuffer(), uri[1], "?dataSourceId={dataSourceId}");

        Object obj = restTemplate.getForObject(url, Object.class, dataSourceId);
        if (ObjectUtil.isNull(obj)) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.NULL_OBJECT);
        }

        ReturnDto returnDto = JSONObject.parseObject(JSONObject.toJSONString(obj), ReturnDto.class);

        return ReturnVo.ok().put(returnDto.getData());
    }

    /**
     * ?dataSourceId=source_1658913892465&schema=DEVELOP&tblName=XXX&startIndex=0&endIndex=6&searchType=0/1
     * @param schema
     * @return
     * @throws GlobalException
     */
    @GetMapping("/query-tables")
    public ReturnVo queryDataSourceTables(@RequestParam String dataSourceId, @RequestParam String schema, @RequestParam String searchType, String tblName) throws GlobalException {
        if (StringUtils.isNullOrEmpty(dataSourceId) || StringUtils.isNullOrEmpty(schema)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        String[] uri = resourceProperty.getUris();
        if (uri == null || uri.length == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        String url = StringUtil.concat(new StringBuffer(), uri[2], "?dataSourceId={dataSourceId}&schema={schema}&tblName={tblName}&startIndex=0&endIndex=6&searchType={searchType}");

        Object obj = restTemplate.getForObject(url, Object.class, dataSourceId, schema, tblName, searchType);
        if (ObjectUtil.isNull(obj)) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.NULL_OBJECT);
        }

        RuleTblDto ruleTblDto = JSONObject.parseObject(JSONObject.toJSONString(obj), RuleTblDto.class);

        return ReturnVo.ok().put(ruleTblDto.getRuleTblList()).put("count", ruleTblDto.getRuleTblCount());
    }

    @GetMapping("/query-columns")
    public ReturnVo queryDataSourceColumns(@RequestParam String dataSourceId, @RequestParam String schema, @RequestParam String tableName) throws GlobalException {
        if (StringUtils.isNullOrEmpty(dataSourceId) || StringUtils.isNullOrEmpty(schema) || StringUtils.isNullOrEmpty(tableName)) {
            throw new GlobalException(ReturnEnum.PARAM_ERROR.getCode(), ExpConstants.PARAMS_EXP);
        }

        String[] uri = resourceProperty.getUris();
        if (uri == null || uri.length == 0) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.RESOURCE_EXP);
        }

        String url = StringUtil.concat(new StringBuffer(), uri[3], "?dataSourceId={dataSourceId}&schema={schema}&tableName={tableName}");

        Object obj = restTemplate.getForObject(url, Object.class, dataSourceId, schema, tableName);

        ReturnDto returnDto = JSONObject.parseObject(JSONObject.toJSONString(obj), ReturnDto.class);

        return ReturnVo.ok().put(returnDto.getData());
    }


}
