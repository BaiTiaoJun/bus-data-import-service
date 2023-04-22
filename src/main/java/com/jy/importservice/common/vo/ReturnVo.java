package com.jy.importservice.common.vo;

import com.jy.importservice.common.enums.ReturnEnum;

import java.util.HashMap;

/**
 * @类名 ResultVo
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/12 19:42
 * @版本 1.0
 */
public class ReturnVo extends HashMap<String, Object> {

    public static ReturnVo error() {
        return error(ReturnEnum.SERVER_ERROR.getCode(), ReturnEnum.SERVER_ERROR.getMsg());
    }

    public static ReturnVo error(String msg) {
        return error(ReturnEnum.SERVER_ERROR.getCode(), msg);
    }

    public static ReturnVo error(int code, String msg) {
        ReturnVo returnVo = new ReturnVo();
        returnVo.put("code", code);
        returnVo.put("msg", msg);
        return returnVo;
    }

    public static ReturnVo ok() {
        return ok(ReturnEnum.SUCCESS.getCode(), ReturnEnum.SUCCESS.getMsg());
    }

    public static ReturnVo ok(String msg) {
        return ok(ReturnEnum.SUCCESS.getCode(), msg);
    }

    public static ReturnVo ok(int code, String msg) {
        ReturnVo returnVo = new ReturnVo();
        returnVo.put("code", code);
        returnVo.put("msg", msg);
        return returnVo;
    }

    public ReturnVo put(Object value) {
        super.put("data", value);
        return this;
    }

    public ReturnVo put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
