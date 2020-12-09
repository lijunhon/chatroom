package org.springboot.chatroom.service.impl;

import org.springboot.chatroom.service.MailService;
import org.springboot.chatroom.utils.MailUtil;
import org.springboot.chatroom.vo.MailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    MailUtil mailUtil;

    @Override
    public boolean sendMail(MailVo mailVo) {
        if(mailUtil.sendMail(mailVo) != null){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void receiveMail(MailVo mailVo) {

    }

    @Override
    public boolean deleteMail(String id) {
        return false;
    }

    @Override
    public boolean saveMail(MailVo mailVo) {
        return false;
    }

    @Override
    public boolean sendMailAtSpecifiedTime(MailVo mailVo, Date date) {
        return false;
    }
}
