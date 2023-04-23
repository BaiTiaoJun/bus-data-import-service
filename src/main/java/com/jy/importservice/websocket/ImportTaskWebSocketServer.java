package com.jy.importservice.websocket;

import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.enums.TaskStatusEnum;
import com.jy.importservice.common.util.BeanFactoryUtil;
import com.jy.importservice.common.util.WebSocketServerEncoderUtil;
import com.jy.importservice.common.vo.ReturnVo;
import com.jy.importservice.entity.ImportSubTask;
import com.jy.importservice.service.ImportSubTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @类名 ImportTaskWebSocketController
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/23 10:47
 * @版本 1.0
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket/query", encoders = WebSocketServerEncoderUtil.class)
public class ImportTaskWebSocketServer {

    private static final ConcurrentHashMap<String, ImportTaskWebSocketServer> CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    private Session session;

    private String sessionId;

    @OnOpen
    public void open(Session session) throws EncodeException, IOException {
        this.session = session;
        this.sessionId = session.getId();
        if (CONCURRENT_HASH_MAP.containsKey(sessionId)) {
            CONCURRENT_HASH_MAP.remove(sessionId);
            CONCURRENT_HASH_MAP.put(sessionId, this);
        } else {
            CONCURRENT_HASH_MAP.put(sessionId, this);
        }

        this.sendMsg(ReturnVo.ok());
        log.info("连接成功，客户端id为：{}", this.sessionId);
    }

    @OnClose
    public void close() {
        CONCURRENT_HASH_MAP.remove(sessionId);
        log.info("关闭成功，客户端id：{}", this.sessionId);
    }

    private void sendMsg(ReturnVo returnVo) throws EncodeException, IOException {
        session.getBasicRemote().sendObject(returnVo);
    }

    @OnMessage
    public void message(String message) throws EncodeException, IOException {
        if (!"get-subtask".equals(message)) {
            this.sendMsg(ReturnVo.error().put(ExpConstants.PARAMS_EXP));
        }
        ImportSubTaskService importSubTaskService = BeanFactoryUtil.getBean(ImportSubTaskService.class);
        List<ImportSubTask> importSubTasks = importSubTaskService.querySubtasksByTaskStatus(String.valueOf(TaskStatusEnum.NOT_START.getCode()));
        ImportTaskWebSocketServer importTaskWebSocketServer = CONCURRENT_HASH_MAP.get(sessionId);
        if (importTaskWebSocketServer.session.isOpen()) {
            importTaskWebSocketServer.sendMsg(ReturnVo.ok().put(importSubTasks).put("totalSize", importSubTasks.size()));
        }
    }
}