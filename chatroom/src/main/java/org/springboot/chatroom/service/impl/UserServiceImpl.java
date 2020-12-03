package org.springboot.chatroom.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springboot.chatroom.dao.UserDao;
import org.springboot.chatroom.dto.UserLoginDto;
import org.springboot.chatroom.po.UserPo;
import org.springboot.chatroom.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Object> getAllUsers(int pageNumber, int pageSize) {
        //将参数传给这个方法就可以实现物理分页
        PageHelper.startPage(pageNumber, pageSize);
        PageInfo<UserPo> pageInfo = new PageInfo<>(userDao.getAllUsers());
        Long total = pageInfo.getTotal();
        List<UserPo> users = pageInfo.getList();
        Map<String,Object> map = new HashMap<>();
        map.put("total", total);
        map.put("data", users);
        return map;
    }

    @Override
    public UserPo getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public UserPo getUserByAccountNumber(String accountNumber) {
        return userDao.getUserByAccountNumber(accountNumber);
    }

    @Override
    public UserLoginDto getLoginUserByAccountNumber(String accountNumber) {
        UserPo userPo = userDao.getUserByAccountNumber(accountNumber);
        if(userPo != null){
            UserLoginDto userLoginPo = new UserLoginDto();
            //复制属性
            BeanUtils.copyProperties(userPo, userLoginPo);
            return userLoginPo;
        }
        return null;
    }

    @Override
    public void registerUser(UserPo userPo) throws IOException {
        //设置在线状态 0在线 1忙碌 2勿扰 3离线
        userPo.setRole("user");
        userPo.setCreateTime(new Date());
        userPo.setOnlineStatus(3);
        userDao.saveUser(userPo);
    }

    @Override
    public void updateUser(UserPo userPo) throws IOException {
        userDao.updateUserById(userPo);
    }

    @Override
    public void deleteUserById(Long id) throws IOException {
        userDao.deleteUserById(id);
    }

    @Override
    public void deleteUserByAccountNumber(String accountNumber) throws IOException {
        userDao.deleteUserByAccountNumber(accountNumber);
    }


}
