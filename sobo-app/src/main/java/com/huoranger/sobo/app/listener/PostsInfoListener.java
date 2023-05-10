package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.repository.PostsRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 **/
@Component
public class PostsInfoListener extends EventBus.EventHandler<BasePosts> {

    @Resource
    private PostsRepository postsRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.POSTS_INFO;
    }

    @Override
    public void onMessage(BasePosts posts) {
        postsRepository.increaseViews(posts.getId(), posts.getUpdateAt());
    }
}
