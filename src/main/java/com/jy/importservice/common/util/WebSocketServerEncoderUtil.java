package com.jy.importservice.common.util;

import com.alibaba.fastjson.JSONObject;
import com.jy.importservice.common.vo.ReturnVo;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @类名 ServerEncoder
 * @描述 websocket编码器，解决"没有为retutnvo对象指定一个编码器"问题
 * @作者 白条君
 * @创建日期 2023/4/23 11:43
 * @版本 1.0
 */
public class WebSocketServerEncoderUtil implements Encoder.Text<ReturnVo> {

    @Override
    public String encode(ReturnVo object) {
        return JSONObject.toJSONString(object);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
