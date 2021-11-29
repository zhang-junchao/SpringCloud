package com.ajc.producer.util;

import org.apache.ibatis.ognl.DefaultMemberAccess;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.ognl.OgnlException;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author : zhangjunchao
 * @version : v4.0
 * @description TODO
 * @date : 2021/11/9 3:57 下午
 */
public class WebSocketUtil {
    public static InetSocketAddress getRemoteAddress(Session session) {
        if (session == null) {
            return null;
        }
        Map<String, Object> userProperties = session.getUserProperties();
        RemoteEndpoint.Async async = session.getAsyncRemote();

        //在Tomcat 8.0.x版本有效
//		InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#sos#socketWrapper#socket#sc#remoteAddress");
        //在Tomcat 8.5以上版本有效
        InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#socketWrapper#socket#sc#remoteAddress");
        return addr;
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            if (obj == null) {
                return null;
            }
        }

        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
        }

        return null;
    }

    private static OgnlContext context = new OgnlContext();
    /**
     * set DefaultMemberAccess with allowed access into the context
     */
    static {
        context.setMemberAccess(new DefaultMemberAccess(true));
    }
    public static String getRemoteAddress2(final Session session) {
        //.getAddress().getHostAddress()
        //.holder.addr.hostName
        //.holder.addr.holder.address
        //.holder.addr.holder.hostName
        //return (String) eval(session,"#root.channel.remoteAddress");
        return eval(session, "#root.channel.remoteAddress.getAddress().getHostAddress()", String.class);
    }


    public static <T> T eval(final Object source, final String expression, Class<T> targetClass) {
        try {
            return (T) Ognl.getValue(expression, context, source);
        } catch (OgnlException e) {
            System.out.println("评估表达式出错:{}");
            throw new IllegalAccessError("expression invalid");
        }
    }



    public static Object eval(final Object source, final String expression) {
        Object value = null;
        try {
            value = Ognl.getValue(expression, context, source);
        } catch (OgnlException e) {
            System.out.println("评估表达式出错:{}");
        }
        return value;
    }


}
