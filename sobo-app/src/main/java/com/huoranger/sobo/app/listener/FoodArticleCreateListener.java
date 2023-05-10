package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.common.enums.FollowedTypeEn;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.Article;
import com.huoranger.sobo.domain.entity.UserFood;
import com.huoranger.sobo.domain.repository.UserFollowRepository;
import com.huoranger.sobo.domain.repository.UserFoodRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huoranger
 **/
@Component
public class FoodArticleCreateListener extends EventBus.EventHandler<Article> {

    @Resource
    private UserFollowRepository userFollowRepository;

    @Resource
    private UserFoodRepository userFoodRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.ARTICLE_CREATE;
    }

    @Override
    public void onMessage(Article article) {
        List<Long> followedIds = userFollowRepository.getAllFollowerIds(article.getAuthorId(), FollowedTypeEn.USER);
        if (ObjectUtils.isEmpty(followedIds)) {
            return;
        }

        List<UserFood> userFoods = followedIds.stream().map(userId -> {
            return UserFood.builder()
                    .postsId(article.getId())
                    .userId(userId)
                    .build();
        }).collect(Collectors.toList());

        userFoodRepository.batchSave(userFoods);
    }
}
