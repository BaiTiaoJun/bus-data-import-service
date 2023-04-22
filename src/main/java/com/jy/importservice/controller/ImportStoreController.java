package com.jy.importservice.controller;

import com.jy.importservice.common.dto.ImportStoreDto;
import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.service.ImportStoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @类名 ImportStoreController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 11:33
 * @版本 1.0
 */
@RestController
@RequestMapping("/store")
public class ImportStoreController {

    private final ImportStoreService importStoreService;

    public ImportStoreController(ImportStoreService importStoreService) {
        this.importStoreService = importStoreService;
    }

    @GetMapping("/query-all-names")
    public ReturnVo queryAllNames() throws GlobalException {
        List<ImportStoreDto> namesDtos = importStoreService.queryAllNames();
        namesDtos = namesDtos.stream().peek(importStoreDto -> importStoreDto.setStore_type(null)).collect(Collectors.toList());
        return ReturnVo.ok().put(namesDtos);
    }
}
