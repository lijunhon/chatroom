package org.springboot.chatroom.controller;

import org.springboot.chatroom.dto.ReturnMessageDto;
import org.springboot.chatroom.service.MailService;
import org.springboot.chatroom.vo.MailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping(value = "/sendmail")
    public ReturnMessageDto sendMail(@RequestBody final MailVo mailVo){

        if(mailService.sendMail(mailVo)){
            return new ReturnMessageDto("SUCCESS","发送邮件成功");
        }else {
            return new ReturnMessageDto("ERROR","发送邮件失败");
        }
    }
}
