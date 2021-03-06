package org.springboot.chatroom.dto;

import lombok.Data;

/**
 * 用户名密码传输实体
 */
@Data
public class UserLoginDto {
    private String accountNumber;
    private String password;
}
