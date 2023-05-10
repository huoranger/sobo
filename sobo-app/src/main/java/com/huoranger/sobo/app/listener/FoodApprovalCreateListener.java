package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.app.support.Pair;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.UserFood;
import com.huoranger.sobo.domain.repository.UserFoodRepository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author huoranger
 **/
@Component
public class FoodApprovalCreateListener extends EventBus.EventHandler<Pair<Long>> {

    @Resource
    private UserFoodRepository userFoodRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.APPROVAL_CREATE;
    }

    @Override
    public void onMessage(Pair<Long> pair) {
        Long userId = pair.getValue0();
        Long postsId = pair.getValue1();

        List<UserFood> userFoods = Arrays.asList(UserFood.builder()
                .postsId(postsId)
                .userId(userId)
                .build());

        userFoodRepository.batchSave(userFoods);
    }
}
