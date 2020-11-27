package org.springboot.chatroom.dto;


import lombok.Data;

/**
 * 服务器发送给客户端的消息
 * 系统消息格式： {"system":true, "fromName":"null", "message":["张三","李四"]}
 * 用户消息格式： {"system":false, "fromName":"张三", "message":"你好"}
 */
@Data
public class ServerMessageDto {
    private boolean system;
    private String fromName;
    private Object message;//用户消息和系统消息类型不一样
}
