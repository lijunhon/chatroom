package org.springboot.chatroom.dto;

import lombok.Data;

/**
 * 用于登录时给浏览器返回数据
 * 200 登录/注册/修改成功
 * 300 用户名或密码不正确
 * 301 用户不存在
 */
@Data
public class ReturnMessageDto {
    private Integer code;
    private String message;
}
