package org.springboot.chatroom.dto;

import lombok.Data;

/**
 * 用于登录时给浏览器返回数据
 * 200 登录/注册/修改成功
 * 300 用户名或密码不正确
 * 301 用户不存在
 * 302 注册账户已存在
 * 303 更新时间错误
 * 304 生成token错误
 * 305 数据库异常
 * 306 注册错误
 * 307 删除用户失败
 * 308 无权限删除用户
 * 309 未授权访问
 * 310 发送邮件失败
 * 400 bad Request
 * 401 全局异常捕捉处理
 * 404 NOT_FOUND
 * 405 Http Method 异常
 * 500 自定义异常捕获
 */
@Data
public class ReturnMessageDto {
    private Integer code;
    private String  status;
    private String message;

    public ReturnMessageDto(Integer code, String status, String message){
        this.setCode(code);
        this.setStatus(status);
        this.setMessage(message);
    }
}
