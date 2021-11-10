package com.ajc.producer.websocket;

import com.ajc.producer.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2021/11/9 4:45 下午
 */
@Component
@Slf4j
@ServerEndpoint(value = "/test/two")
public class WebSocketServer2
    {
        /**
         * 静态变量，用来记录在线连接数，应该把它设计成线程安全的
         */
        private static AtomicInteger onlineCount = new AtomicInteger(0);

        /**
         * 线程安全Set
         */
        private static CopyOnWriteArraySet<WebSocketServer2> webSocketSet = new CopyOnWriteArraySet<WebSocketServer2>();

        /**
         * 与某个客户端的连接会话，需要通过它来给客户端发送数据
         */
        private Session session ;

        /**
         * 连接建立成功调用的方法
         *
         * @param session
         */
        @OnOpen
        public void onOpen(Session session) {
        this.session = session;
        //获取客户端连接信息
        InetSocketAddress remoteAddress = WebSocketUtil.getRemoteAddress(session);
        Principal userPrincipal = session.getUserPrincipal();
        //加入set中
        webSocketSet.add(this);
        //添加在线人数
        addOnlineCount();
        System.out.println("新连接接入。当前在线人数为：" + getOnlineCount());
    }

        /**
         * 连接关闭调用的方法
         */
        @OnClose
        public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        System.out.println("有连接关闭。当前在线人数为：" + getOnlineCount());
    }

        /**
         * 收到客户端消息后调用
         *
         * @param message
         * @param session
         */
        @OnMessage
        public void onMessage(String message, Session session) {
        System.out.println("客户端发送的消息：" + message);
        sendAll(message);
    }

        /**
         * 暴露给外部的群发
         *
         * @param message
         * @throws IOException
         */
        public static void sendInfo(String message) throws IOException {
        sendAll(message);
    }

        /**
         * 群发
         *
         * @param message
         */
        private static void sendAll(String message) {
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            WebSocketServer2 customWebSocket = (WebSocketServer2) item;
            //群发
            try {
                customWebSocket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

        /**
         * 发送信息
         *
         * @param message
         * @throws IOException
         */
        public void sendMessage(String message) throws IOException {
        //获取session远程基本连接发送文本消息
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }



        /**
         * 发生错误时调用
         *
         * @param session
         * @param error
         */
        @OnError
        public void onError(Session session, Throwable error) {
        log.error("----websocket-------有异常啦");
        error.printStackTrace();
    }

        /**
         * 减少在线人数
         */
        private void subOnlineCount() {
        WebSocketServer2.onlineCount.decrementAndGet();
    }

        /**
         * 添加在线人数
         */
        private void addOnlineCount() {
        WebSocketServer2.onlineCount.incrementAndGet();
    }

        /**
         * 当前在线人数
         *
         * @return
         */
        public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }


}
