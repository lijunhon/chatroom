package org.springboot.chatroom.dto;

import lombok.Data;

import java.util.Date;

/**
 * 用户注册传输实体
 */
@Data
public class UserRegisterDto {
    //账号
    private String accountNumber;
    //昵称
    private String nickname;
    //密码
    private String password;
    //邮箱
    private String email;
    //个性签名
    private String signature;
    //城市
    private String city;
}
