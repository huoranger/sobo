package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.OptLog;
import com.huoranger.sobo.domain.entity.User;
import com.huoranger.sobo.domain.repository.OptLogRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/10/22
 * @desc
 **/
@Component
public class OptLogUserRegisterListener extends EventBus.EventHandler<User> {

    @Resource
    private OptLogRepository optLogRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.USER_REGISTER;
    }

    @Override
    public void onMessage(User user) {

        // 保存操作记录
        optLogRepository.save(OptLog.createUserRegisterRecordLog(user.getId(), user));
    }
}
