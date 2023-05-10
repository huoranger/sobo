package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.repository.UserFoodRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 **/
@Component
public class FoodPostsDeleteListener extends EventBus.EventHandler<BasePosts> {

    @Resource
    private UserFoodRepository userFoodRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.POSTS_DELETE;
    }

    @Override
    public void onMessage(BasePosts basePosts) {
        userFoodRepository.deleteByPostsId(basePosts.getId());
    }
}
