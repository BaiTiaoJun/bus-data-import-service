package com.jy.importservice.controller;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportSqlldrInfo;
import com.jy.importservice.service.ImportSqlldrInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @类名 ImportDataSourceController
 * @描述 sqlldr信息API
 * @作者 白条君
 * @创建日期 2023/4/12 19:41
 * @版本 1.0
 */
@RestController
@RequestMapping("/sqlldr")
public class ImportSqlldrInfoController {

    private final ImportSqlldrInfoService importSqlldrInfoService;

    public ImportSqlldrInfoController(ImportSqlldrInfoService importSqlldrInfoService) {
        this.importSqlldrInfoService = importSqlldrInfoService;
    }

    @GetMapping("/query-by-page")
    public ReturnVo queryByPage(@RequestParam(defaultValue = "1") Long pageNo
            , @RequestParam(defaultValue = "10") Long pageSize
            , String sqlldrInfoName) {
        return importSqlldrInfoService.queryByPage(pageNo, pageSize, sqlldrInfoName);
    }

    @GetMapping("/query-all-names")
    public ReturnVo queryAllNames() {
        List<ImportSqlldrInfo> allNames = importSqlldrInfoService.queryAllNames();
        return ReturnVo.ok().put(allNames);
    }

    @PostMapping("/addition")
    public ReturnVo add(@RequestBody ImportSqlldrInfo importSqlldrInfo) throws GlobalException {
        importSqlldrInfoService.addSqlldrInfo(importSqlldrInfo);
        return ReturnVo.ok();
    }

    @PutMapping("/modification")
    public ReturnVo modify(@RequestBody ImportSqlldrInfo importSqlldrInfo) throws GlobalException {
        importSqlldrInfoService.modifySqlldrInfo(importSqlldrInfo);
        return ReturnVo.ok();
    }

    @DeleteMapping("/deletion")
    public ReturnVo delete(@RequestParam Long id) throws GlobalException {
        importSqlldrInfoService.deleteSqlldrInfo(id);
        return ReturnVo.ok();
    }
}
