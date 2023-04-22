package com.jy.importservice.handler;

import com.jy.importservice.common.exception.GlobalException;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.vo.ReturnVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @类名 GlobalExceptionHandler
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/16 21:55
 * @版本 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnVo exp(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return ReturnVo.error(globalException.getCode(), globalException.getMessage());
        } else {
            return ReturnVo.error(ReturnEnum.SERVER_ERROR.getCode(), e.getMessage());
        }
    }
}
