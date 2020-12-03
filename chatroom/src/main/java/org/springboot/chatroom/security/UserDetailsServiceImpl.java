package org.springboot.chatroom.security;

import org.springboot.chatroom.dao.UserDao;
import org.springboot.chatroom.po.UserPo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserDao UserDao;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        UserPo userPo = UserDao.getUserByAccountNumber(accountNumber);
        if (userPo == null ) {
            throw new UsernameNotFoundException(accountNumber);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // Spring Security 角色名称默认使用 "ROLE_" 开头
        // authorities.add 可以增加多个用户角色，对于一个用户有多种角色的系统来说，
        // 可以通过增加用户角色表、用户--角色映射表，存储多个用户角色信息
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userPo.getRole()));
        // 给 Spring Security 传入用户名、用户密码、用户角色。
        return new User(userPo.getAccountNumber(), userPo.getPassword(), authorities);
    }
}
