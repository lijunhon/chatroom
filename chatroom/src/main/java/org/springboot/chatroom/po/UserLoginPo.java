package org.springboot.chatroom.po;

import lombok.Data;

/**
 * 用户名密码数据库实体
 */
@Data
public class UserLoginPo {
    private String accountNumber;
    private String password;
}
