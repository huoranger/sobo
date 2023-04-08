package com.huoranger.sobo.domain.service;

import com.huoranger.sobo.domain.entity.Message;

/**
 * @author huoranger
 * @create 2020/12/7
 * @desc
 **/
public interface MailService {

    void sendHtml(Message mailMessage);

    void sendText(Message mailMessage);

}
