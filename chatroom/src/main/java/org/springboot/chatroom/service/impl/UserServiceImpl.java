package org.springboot.chatroom.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springboot.chatroom.dao.UserDao;
import org.springboot.chatroom.po.UserLoginPo;
import org.springboot.chatroom.po.UserPo;
import org.springboot.chatroom.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserLoginPo getLoginUserByAccountNumber(String accountNumber) {
        UserPo userPo = userDao.getUserByAccountNumber(accountNumber);
        if(userPo != null){
            UserLoginPo userLoginPo = new UserLoginPo();
            //复制属性
            BeanUtils.copyProperties(userPo, userLoginPo);
            return userLoginPo;
        }
        return null;
    }


}
