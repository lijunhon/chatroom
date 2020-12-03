package org.springboot.chatroom.service;

import org.springboot.chatroom.dto.UserLoginDto;
import org.springboot.chatroom.po.UserPo;

import java.io.IOException;
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
     * 根据accountNumber查询用户
     */
    UserPo getUserByAccountNumber(String accountNumber);
    /**
     * 查询用户名密码
     */
    UserLoginDto getLoginUserByAccountNumber(String accountNumber);

    /**
     * 注册用户
     */
    void registerUser(UserPo userPo) throws IOException;
    /**
     * 更新用户
     */
    void updateUser(UserPo userPo) throws IOException;
    /**
     * 通过id删除用户
     */
    void deleteUserById(Long id) throws IOException;
    /**
     * 通过accountNumber删除用户
     */
    void deleteUserByAccountNumber(String accountNumber) throws IOException;
}
