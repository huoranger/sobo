package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.enums.MessageChannelEn;
import com.huoranger.sobo.common.enums.MessageTypeEn;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.Message;
import com.huoranger.sobo.domain.entity.User;
import com.huoranger.sobo.domain.service.MessageService;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/4
 * @desc
 **/
@Component
public class NotifyAdminUserRegisterListener extends EventBus.EventHandler<User> {

    @Resource
    private MessageService messageService;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.USER_REGISTER;
    }

    @Override
    public void onMessage(User user) {

        // 发送消息通知
        messageService.send(Message.builder()
                .channel(MessageChannelEn.MAIL)
                .type(MessageTypeEn.USER_REGISTER_NOTIFY_ADMIN)
//                .sender(IdValue.builder()
//                        .id()
//                        .name()
//                        .type(IdValueTypeEn.MAIL)
//                        .build())
//                .receiver(IdValue.builder()
//                        .id()
//                        .name()
//                        .type(IdValueTypeEn.MAIL)
//                        .build())
                .title("")
                .content("")
                .build());
    }
}
