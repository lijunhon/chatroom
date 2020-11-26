package org.springboot.chatroom.dto;

import lombok.Data;

/**
 * 用户注册传输实体
 */
@Data
public class UserRegisterDto {
    private String username;
    private String password;
    private String email;
}
