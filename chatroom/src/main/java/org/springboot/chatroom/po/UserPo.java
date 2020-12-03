package org.springboot.chatroom.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户持久化实体类
 */
@Data
public class UserPo implements Serializable {
    //用户id
    private long id;
    //用户名
    private String accountNumber;
    //昵称
    private String nickname;
    //密码
    private String password;
    //近期密码
    private String recentPassword;
    //邮箱
    private String email;
    //个性签名
    private String signature;
    //城市
    private String city;
    //登录状态 0在线 1忙碌 2勿扰 3离线
    private Integer onlineStatus;
    //上次登录时间
    private Date lastLoginTime;
    //创建时间
    private Date createTime;
    //角色
    private String role;
}
