package org.springboot.chatroom.dao;

import lombok.Data;

/**
 * 用户持久化实体类
 */
@Data
public class UserDao {
    //用户名
    private String username;
    //昵称
    private String nickname;
    //密码
    private String password;
    //邮箱
    private String email;
}
