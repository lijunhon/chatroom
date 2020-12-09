package org.springboot.chatroom.service;

import org.springboot.chatroom.vo.MailVo;

import java.util.Date;

public interface MailService {
    /**
     * 发送邮件
     * @param mailVo
     * @return 是否成功
     */
    boolean sendMail(MailVo mailVo);

    /**
     * 接收邮件
     * @param mailVo
     */
    void receiveMail(MailVo mailVo);

    /**
     * 删除邮件
     * @param id
     * @return 是否成功
     */
    boolean deleteMail(String id);

    /**
     * 保存邮件
     * @param mailVo
     * @return 是否成功
     */
    boolean saveMail(MailVo mailVo);

    /**
     * 定时发送
     * @param mailVo
     * @param date
     * @return 是否成功
     */
    boolean sendMailAtSpecifiedTime(MailVo mailVo, Date date);

}
