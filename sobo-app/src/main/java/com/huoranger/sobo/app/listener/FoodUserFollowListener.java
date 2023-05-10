package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.app.support.Pair;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.UserFood;
import com.huoranger.sobo.domain.repository.PostsRepository;
import com.huoranger.sobo.domain.repository.UserFoodRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huoranger
 **/
@Component
public class FoodUserFollowListener extends EventBus.EventHandler<Pair<Long>> {

    @Resource
    private PostsRepository postsRepository;

    @Resource
    private UserFoodRepository userFoodRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.USER_FOLLOW;
    }

    @Override
    public void onMessage(Pair<Long> pair) {
        Long followed = pair.getValue0();
        Long follower = pair.getValue1();

        List<Long> postsIds = postsRepository.getAllIdByAuthorId(followed);
        if (ObjectUtils.isEmpty(postsIds)) {
            return;
        }

        List<UserFood> userFoods = postsIds.stream().map(postsId -> {
            return UserFood.builder()
                    .postsId(postsId)
                    .userId(follower)
                    .build();
        }).collect(Collectors.toList());

        userFoodRepository.batchSave(userFoods);
    }
}
