package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.app.support.Pair;
import com.huoranger.sobo.common.enums.*;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.entity.Message;
import com.huoranger.sobo.domain.entity.value.IdValue;
import com.huoranger.sobo.domain.repository.MessageRepository;
import com.huoranger.sobo.domain.repository.PostsRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
@Component
public class MessageApprovalCreateListener extends EventBus.EventHandler<Pair<Long>> {

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private PostsRepository postsRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.APPROVAL_CREATE;
    }

    @Override
    public void onMessage(Pair<Long> pair) {
        Long userId = pair.getValue0();
        Long postsId = pair.getValue1();

        BasePosts basePosts = postsRepository.get(postsId);
        if (ObjectUtils.isEmpty(basePosts)) {
            return;
        }

        Message message = Message.builder()
                .channel(MessageChannelEn.STATION_LETTER)
                .receiver(IdValue.builder()
                        .id(basePosts.getAuthorId().toString())
                        .type(IdValueTypeEn.USER_ID)
                        .build())
                .read(MessageReadEn.NO)
                .contentType(MessageContentTypeEn.TEXT)
                .title(postsId.toString())
                .content("")
                .sender(IdValue.builder()
                        .id(userId.toString())
                        .type(IdValueTypeEn.USER_ID)
                        .build())
                .build();
        if (PostsCategoryEn.ARTICLE.equals(basePosts.getCategory())) {
            message.setType(MessageTypeEn.APPROVAL_ARTICLE);
        } else if (PostsCategoryEn.FAQ.equals(basePosts.getCategory())) {
            message.setType(MessageTypeEn.APPROVAL_FAQ);
        } else {
            return;
        }

        messageRepository.save(message);
    }
}
