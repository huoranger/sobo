package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.enums.MessageTypeEn;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.repository.MessageRepository;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
@Component
public class MessagePostsDeleteListener extends EventBus.EventHandler<BasePosts> {

    @Resource
    private MessageRepository messageRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.POSTS_DELETE;
    }

    @Override
    public void onMessage(BasePosts basePosts) {
        messageRepository.deleteInTypesAndTitle(Arrays.asList(MessageTypeEn.APPROVAL_ARTICLE
                , MessageTypeEn.APPROVAL_FAQ
                , MessageTypeEn.COMMENT_ARTICLE
                , MessageTypeEn.COMMENT_FAQ), basePosts.getId().toString());
    }
}
