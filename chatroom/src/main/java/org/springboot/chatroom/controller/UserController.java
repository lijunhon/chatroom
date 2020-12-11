package org.springboot.chatroom.controller;

import org.springboot.chatroom.dto.ReturnMessageDto;
import org.springboot.chatroom.dto.UserLoginDto;
import org.springboot.chatroom.dto.UserRegisterDto;
import org.springboot.chatroom.po.UserPo;
import org.springboot.chatroom.security.TokenUtil;
import org.springboot.chatroom.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("a");
        try {
            // 验证用户名和密码是否对的
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getAccountNumber(),
                            userLoginDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ReturnMessageDto(300,"ERROR", "用户名或者密码不正确");
        }
        // 生成Token与查询用户权限
        UserPo userPo = userService.getUserByAccountNumber(userLoginDto.getAccountNumber());
        userPo.setLastLoginTime(new Date());
        try {
            userService.updateUser(userPo);
        } catch (IOException e) {
            return new ReturnMessageDto(303,"ERROR", "更新登录时间错误");
        }
        String token = null;
        try{
            token = tokenUtil.createToken(userPo);
        }catch (Exception e){
            return new ReturnMessageDto(304, "ERROR", "生成token错误");
        }
        return new ReturnMessageDto(200,"SUCCESS",
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
            return new ReturnMessageDto(305,"ERROR", "数据库异常");
        }
        catch (DuplicateKeyException e){
            return new ReturnMessageDto(302,"ERROR", "已存在用户名或邮箱");
        }
        catch (Exception e) {
            return new ReturnMessageDto(306,"ERROR", "注册错误或者用户权限出错");
        }
        return new ReturnMessageDto(200,"SUCCESS", "用户新增成功");
    }

    @PostMapping(value = "/deleteuser")
    public ReturnMessageDto deleteUserById(@PathVariable Long id){
        //判断删除的是否是当前用户
        if("admin".equals(userService.getUserById(id).getRole())){
            try {
                userService.deleteUserById(id);
            } catch (IOException e) {
                return new ReturnMessageDto(307,"ERROR","删除用户失败，数据库原因");
            }
            return new ReturnMessageDto(200,"SUCCESS", "删除成功");
        }
        return new ReturnMessageDto(308,"ERROR","无权限删除用户");
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

