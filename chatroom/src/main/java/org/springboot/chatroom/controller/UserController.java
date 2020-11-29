package org.springboot.chatroom.controller;

import org.springboot.chatroom.dto.ReturnMessageDto;
import org.springboot.chatroom.dto.UserLoginDto;
import org.springboot.chatroom.po.UserLoginPo;
import org.springboot.chatroom.po.UserPo;
import org.springboot.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 提供关于用户相关的增删改查Restful接口
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 查询全部用户
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Map<String, Object> getAllUsers(
            @RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "2", required = false) int pageSize){
        return userService.getAllUsers(pageNumber, pageSize);
    }

    /**
     * 查询单个用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserPo getUserById(@PathVariable Long id){
        UserPo userPo = userService.getUserById(id);
        return userPo;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ReturnMessageDto loginByPassword(@RequestBody UserLoginDto userLoginDto){
        //返回结果 是否成功
        ReturnMessageDto returnMessageDto = new ReturnMessageDto();
        //查找用户
        UserLoginPo userLoginPo = userService.getLoginUserByAccountNumber(userLoginDto.getAccountNumber());

        if(userLoginPo == null){
            returnMessageDto.setCode(301);
            returnMessageDto.setMessage("用户不存在！");
        }else if(userLoginDto.getPassword().equals(userLoginPo.getPassword())){
            returnMessageDto.setCode(200);
            returnMessageDto.setMessage("登录成功！");
        }else {
            returnMessageDto.setCode(300);
            returnMessageDto.setMessage("用户名或密码错误！");
        }

        return returnMessageDto;
    }

}
