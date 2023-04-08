package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.OptLog;
import com.huoranger.sobo.domain.repository.OptLogRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/4
 * @desc
 **/
@Component
public class OptLogUserLogoutListener extends EventBus.EventHandler<OptLog> {

    @Resource
    private OptLogRepository optLogRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.USER_LOGOUT;
    }

    @Override
    public void onMessage(OptLog optLog) {
        optLogRepository.save(optLog);
    }
}
