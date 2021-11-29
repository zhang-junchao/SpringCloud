package com.ajc.producer.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2021/11/11 2:24 下午
 */
public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {
    public static final String IP_ADDR = "IP.ADDR";

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

        Map<String, Object> attributes = sec.getUserProperties();
        HttpSession session = (HttpSession) request.getHttpSession();
        if (session != null) {
            attributes.put(IP_ADDR, session.getAttribute("ip"));
            Enumeration<String> names = session.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                attributes.put(name, session.getAttribute(name));
            }
        }
    }

}
