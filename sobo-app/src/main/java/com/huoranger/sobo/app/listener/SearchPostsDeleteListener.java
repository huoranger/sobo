package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.service.SearchService;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/3
 * @desc
 **/
@Component
public class SearchPostsDeleteListener extends EventBus.EventHandler<BasePosts> {

    @Resource
    private SearchService searchService;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.POSTS_DELETE;
    }

    @Override
    public void onMessage(BasePosts basePosts) {
        searchService.deleteByPostsId(basePosts.getId());
    }
}
