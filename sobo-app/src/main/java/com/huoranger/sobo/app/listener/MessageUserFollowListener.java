package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.app.support.Pair;
import com.huoranger.sobo.common.enums.*;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.Message;
import com.huoranger.sobo.domain.entity.value.IdValue;
import com.huoranger.sobo.domain.repository.MessageRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
@Component
public class MessageUserFollowListener extends EventBus.EventHandler<Pair<Long>> {

    @Resource
    private MessageRepository messageRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.USER_FOLLOW;
    }

    @Override
    public void onMessage(Pair<Long> pair) {
        Long followed = pair.getValue0();
        Long follower = pair.getValue1();

        messageRepository.save(Message.builder()
                .channel(MessageChannelEn.STATION_LETTER)
                .type(MessageTypeEn.FOLLOW_USER)
                .receiver(IdValue.builder()
                        .id(followed.toString())
                        .type(IdValueTypeEn.USER_ID)
                        .build())
                .read(MessageReadEn.NO)
                .contentType(MessageContentTypeEn.TEXT)
                .content("")
                .sender(IdValue.builder()
                        .id(follower.toString())
                        .type(IdValueTypeEn.USER_ID)
                        .build())
                .title("")
                .build());
    }
}
