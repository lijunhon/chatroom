package org.springboot.chatroom.utils;

import com.alibaba.fastjson.JSON;
import org.springboot.chatroom.dto.ServerMessageDto;
import org.springboot.chatroom.ws.ChatEndPoint;

import java.io.IOException;
import java.util.Set;

/**
 * 用于封装消息
 */
public class MessageUtils {
    /**
     * @param system
     * @param fromName
     * @param message
     * @return 用户消息json格式
     */
    public static String getServerMessage(boolean system, String fromName, Object message) {
        ServerMessageDto serverMessage = new ServerMessageDto();
        serverMessage.setSystem(system);
        serverMessage.setFromName(fromName);
        serverMessage.setMessage(message);
        //java对象转换为json字符串
        return JSON.toJSONString(serverMessage);

    }

    //广播给所有用户消息
    public static void broadcastAllUsers(String message) throws IOException {
        Set<String> userNames = ChatEndPoint.onlineUsers.keySet();
        for(String name: userNames){
            ChatEndPoint chatEndPoint = (ChatEndPoint) ChatEndPoint.onlineUsers.get(name);
            chatEndPoint.getSession().getBasicRemote().sendText(message);
        }
    }

    //广播给单个用户消息
    public static void broadcastMessage(String message, String toName) throws IOException {
        ChatEndPoint chatEndPoint = (ChatEndPoint) ChatEndPoint.onlineUsers.get(toName);
        chatEndPoint.getSession().getBasicRemote().sendText(message);
    }
}
