package org.springboot.chatroom.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springboot.chatroom.utils.MessageUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/userchat", configurator = getHttpSessionConfigurator.class)
@Component
public class ChatEndPoint {

    public static Map<String, ChatEndPoint> onlineUsers= new ConcurrentHashMap<>();
    private Session session;
    private HttpSession httpSession;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    //连接建立时被调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        //保存当前会话
        setSession(session);
        setHttpSession((HttpSession) config.getUserProperties().get(HttpSession.class.getName()));
        //将当前用户名和当前连接绑定
        onlineUsers.put((String) httpSession.getAttribute("username"),this);

        //系统推送上线消息
        String message = MessageUtils.getServerMessage(true,null, getOnlineNames());
        try {
            MessageUtils.broadcastAllUsers(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接收到消息时调用
    @OnMessage
    public void onMessage(String message,Session session){
        JSONObject mess = JSON.parseObject(message);
        String fromName = (String) httpSession.getAttribute("username");
        String sendMessage = MessageUtils.getServerMessage(false, fromName, mess.get("message"));
        try {
            //推送用户消息
            MessageUtils.broadcastMessage(sendMessage, (String) mess.get("toName"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(Session session){
        String fromName = (String) httpSession.getAttribute("username");
        //将用户从onlineusers除去
        onlineUsers.remove(fromName);
        //广播用户下线
        String message = MessageUtils.getServerMessage(true,null, getOnlineNames());
        try {
            MessageUtils.broadcastAllUsers(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //获取在线用户名
    private Set<String> getOnlineNames(){
        return onlineUsers.keySet();
    }
}
