package org.springboot.chatroom.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;
import org.springboot.chatroom.po.UserPo;

public interface UserDao {

    @Select("select * from users")
    Page<UserPo> getAllUsers();

    @Select("select * from users where id = #{id}")
    UserPo getUserById(Long id);

    @Select("select * from users where account_number = #{accountNumber}")
    UserPo getUserByAccountNumber(String accountNumber);

    @Insert("insert into users(" +
            "account_number, nickname, password, recent_password, email, signature, city, online_status, last_login_time, create_time, role) " +
            "values" +
            "(#{accountNumber}, #{nickname}, #{password}, #{recentPassword}, #{email}, #{signature}, #{city}, #{onlineStatus}, #{lastLoginTime}, #{createTime}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean saveUser(UserPo userPo);

    @Update("update users set " +
            "nickname = #{nickname}, " +
            "password = #{password}, " +
            "recent_password = #{recentPassword}, " +
            "email = #{email}, " +
            "signature = #{signature}, " +
            "city = #{city}, " +
            "online_status = #{onlineStatus}, " +
            "last_login_time = #{lastLoginTime}" +
            "create_time = #{createTime}" +
            "role = #{role}" +
            "where id = #{id}")
    void updateUserById(UserPo userPo);

    @Update("update users set " +
            "nickname = #{nickname}, " +
            "password = #{password}, " +
            "recent_password = #{recentPassword}, " +
            "email = #{email}, " +
            "signature = #{signature}, " +
            "city = #{city}, " +
            "online_status = #{onlineStatus}, " +
            "last_login_time = #{lastLoginTime}" +
            "create_time = #{createTime}" +
            "role = #{role}" +
            "where account_number = #{accountNumber}")
    void updateUserByAccountNumber(UserPo userPo);

    @Delete("delete from users where id = #{id}")
    void deleteUserById(Long id);

    @Delete("delete from users where account_number = #{accountNumber}")
    void deleteUserByAccountNumber(String accountNumber);

}
