package org.springboot.chatroom.service;

import org.springboot.chatroom.po.UserLoginPo;
import org.springboot.chatroom.po.UserPo;

import java.util.Map;

public interface UserService {

    /**
     * 查询所有用户
     */
    Map<String, Object> getAllUsers(int pageNumber, int pageSize);
    /**
     * 根据id查询用户
     */
    UserPo getUserById(Long id);
    /**
     * 查询用户名密码
     */
    UserLoginPo getLoginUserByAccountNumber(String accountNumber);
}
