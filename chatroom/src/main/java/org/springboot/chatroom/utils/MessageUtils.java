package org.springboot.chatroom.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springboot.chatroom.dto.ServerMessageDto;

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
    public static String getServerMessage(boolean system, String fromName, Object message){
        try{
            ServerMessageDto serverMessage = new ServerMessageDto();
            serverMessage.setSystem(system);
            serverMessage.setFromName(fromName);
            serverMessage.setMessage(message);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(serverMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
