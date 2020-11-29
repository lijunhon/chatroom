package org.springboot.chatroom.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Select;
import org.springboot.chatroom.po.UserPo;

public interface UserDao {

    @Select("select * from users")
    Page<UserPo> getAllUsers();

    @Select("select * from users where id = #{id}")
    UserPo getUserById(Long id);

    @Select("select * from users where account_number = #{accountNumber}")
    UserPo getUserByAccountNumber(String accountNumber);


}
