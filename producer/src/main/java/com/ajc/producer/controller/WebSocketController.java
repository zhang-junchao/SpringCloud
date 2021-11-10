package com.ajc.producer.controller;

import com.ajc.producer.websocket.WebSocketServer;
import com.ajc.producer.websocket.WebSocketServer2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2021/11/9 2:55 下午
 */
@RestController
@RequestMapping("/socket")
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer ;

    @Autowired
    private WebSocketServer2 webSocketServer2 ;



    @RequestMapping("/one/msg")
    public String sendController(@RequestParam String msg) {
        try {
            webSocketServer.sendInfo(msg);
            return "消息发送成功！";
        } catch (IOException e) {
            e.printStackTrace();
            return "消息发送失败";
        }

    }

    @RequestMapping("/two/msg")
    public String sendController2(@RequestParam String msg) {
        try {
            webSocketServer2.sendInfo(msg);
            return "消息发送成功！";
        } catch (IOException e) {
            e.printStackTrace();
            return "消息发送失败";
        }

    }

}
