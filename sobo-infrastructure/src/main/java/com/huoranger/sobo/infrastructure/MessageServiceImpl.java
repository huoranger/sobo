package com.huoranger.sobo.infrastructure;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.common.enums.MessageChannelEn;
import com.huoranger.sobo.common.enums.MessageContentTypeEn;
import com.huoranger.sobo.domain.entity.Message;
import com.huoranger.sobo.domain.repository.MessageRepository;
import com.huoranger.sobo.domain.service.MailService;
import com.huoranger.sobo.domain.service.MessageService;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/10/22
 * @desc
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private MailService mailService;

    @Override
    public void send(Message message) {
        // 邮件
        if (MessageChannelEn.MAIL.equals(message.getChannel())) {
            if (MessageContentTypeEn.HTML.equals(message.getContentType())) {
                mailService.sendHtml(message);
            }
            if (MessageContentTypeEn.TEXT.equals(message.getContentType())) {
                mailService.sendText(message);
            }
        }

        // 站内信
        if (MessageChannelEn.STATION_LETTER.equals(message.getChannel())) {
            // do nothing
        }

        messageRepository.save(message);
    }

}
