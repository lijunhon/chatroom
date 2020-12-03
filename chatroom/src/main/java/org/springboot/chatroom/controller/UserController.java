package org.springboot.chatroom.controller;

import org.springboot.chatroom.dto.ReturnMessageDto;
import org.springboot.chatroom.dto.UserLoginDto;
import org.springboot.chatroom.dto.UserRegisterDto;
import org.springboot.chatroom.po.UserPo;
import org.springboot.chatroom.security.TokenUtil;
import org.springboot.chatroom.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * 提供关于用户相关的增删改查Restful接口
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenUtil tokenUtil;

    /** 用户登录
     * @param userLoginDto 用户登录信息
     * @return 用户登录成功返回的Token
     */
    @PostMapping(value = "/login")
    public ReturnMessageDto login(@RequestBody final UserLoginDto userLoginDto) {
        try {
            // 验证用户名和密码是否对的
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getAccountNumber(),
                            userLoginDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ReturnMessageDto("ERROR", "用户名或者密码不正确");
        }
        // 生成Token与查询用户权限
        UserPo userPo = userService.getUserByAccountNumber(userLoginDto.getAccountNumber());
        userPo.setLastLoginTime(new Date());
        //UserLoginDto userDtoData = userService.getLoginUserByAccountNumber(userLoginDto.getAccountNumber());
        String token = null;
        try{
            token = tokenUtil.createToken(userPo);
        }catch (Exception e){
            return new ReturnMessageDto("ERROR", "生成token错误");
        }
        return new ReturnMessageDto("SUCCESS",
                token);
    }

    /** 用户注册
     * @param userRegisterDto 用户注册信息
     * @return 用户注册结果
     */
    @PostMapping(value = "/register")
    public ReturnMessageDto register(@RequestBody @Valid final UserRegisterDto userRegisterDto) {
        UserPo userPo = new UserPo();
        try {
            // 密码加密存储
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(userRegisterDto.getPassword());
            userRegisterDto.setPassword(password);
            BeanUtils.copyProperties(userRegisterDto, userPo);
            userService.registerUser(userPo);
        }catch (IOException e){
            return new ReturnMessageDto("ERROR", "数据库异常");
        }
        catch (DuplicateKeyException e){
            return new ReturnMessageDto("ERROR", "已存在用户名或邮箱");
        }
        catch (Exception e) {
            return new ReturnMessageDto("ERROR", "已经存在该用户名或者邮箱，或者用户权限出错");
        }
        return new ReturnMessageDto("SUCCESS", "用户新增成功");
    }

    /** 这是登录用户才可以看到的内容 */
    @PostMapping(value = "/message")
    public String message() {
        return "这个消息只有登录用户才可以看到";
    }

    /** 这是管理员用户才可以看到 */
    @PostMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "这个消息只有管理员用户才可以看到";
    }
}

