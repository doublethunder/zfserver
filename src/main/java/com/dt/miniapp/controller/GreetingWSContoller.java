package com.dt.miniapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chenlei
 * @date 2019-04-23
 */
@ServerEndpoint(value = "/websocket")
@Component
@Slf4j
public class GreetingWSContoller {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicLong onlineCount = new AtomicLong(0);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<GreetingWSContoller> webSocketSet = new CopyOnWriteArraySet<>();

    private static final int MAX_NUM = 10000;

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        if (webSocketSet.size() > MAX_NUM) {
            CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT,
                    "不好意思，聊天室满员了");
            try {
                session.close(closeReason);
            } catch (IOException e) {
                log.error("session关闭异常", e);
            }
        }
        //在线数加1
        log.info("有新连接加入！当前在线人数为" + addOnlineCount());
        try {
            session.setMaxBinaryMessageBufferSize(1000);
            session.setMaxIdleTimeout(60 * 1000);
            session.setMaxTextMessageBufferSize(100);
            sendMessage("热烈欢迎，来跟我尬聊吧~");
        } catch (IOException e) {
            log.error("IO异常", e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        log.info("有一连接关闭！当前在线人数为" + subOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:{}", message);

        try {
            session.getBasicRemote().sendText(getResponseMessage(message));
        } catch (IOException e) {
            log.error("消息发送错误", e);
        }
    }

    String getResponseMessage(String message) {
        if (StringUtils.isNotEmpty(message)) {
            message = message.replace("吗", "");
            message = message.replace("?", "!");
            message = message.replace("？", "!");
            return message;
        }
        return "";
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (GreetingWSContoller item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static long getOnlineCount() {
        return onlineCount.get();
    }

    public static long addOnlineCount() {
        return onlineCount.incrementAndGet();
    }

    public static long subOnlineCount() {
        return onlineCount.decrementAndGet();
    }
}
