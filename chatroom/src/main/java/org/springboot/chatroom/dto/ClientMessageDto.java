package org.springboot.chatroom.dto;

import lombok.Data;

/**
 * 浏览器发送给服务器的消息
 * 格式： {"toName":"张三", "message":"你好"}
 */
@Data
public class ClientMessageDto {
    private String toName;
    private String message;
}
