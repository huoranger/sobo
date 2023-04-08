package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.enums.PostsCategoryEn;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.repository.ArticleTypeRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/11/25
 * @desc
 **/
@Component
public class PostsDeleteListener extends EventBus.EventHandler<BasePosts> {

    @Resource
    private ArticleTypeRepository articleTypeRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.POSTS_DELETE;
    }

    @Override
    public void onMessage(BasePosts basePosts) {
        // 文章类别引用数减
        if (PostsCategoryEn.ARTICLE.equals(basePosts.getCategory())) {
            articleTypeRepository.decreaseRefCount(basePosts.getTypeId());
        }
    }
}
