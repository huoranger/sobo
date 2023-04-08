package com.huoranger.sobo.app.transfer;

import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.api.request.user.UserOptLogPageRequest;
import com.huoranger.sobo.common.enums.OptLogTypeEn;
import com.huoranger.sobo.domain.entity.OptLog;

/**
 * @author huoranger
 * @create 2020/12/9
 * @desc
 **/
public class OptLogTransfer {

    public static OptLog toOptLog(UserOptLogPageRequest request) {
        return OptLog.builder()
                .operatorId(request.getOperatorId())
                .type(ObjectUtils.isEmpty(request.getType()) ? null : OptLogTypeEn.getEntity(request.getType()))
                .build();
    }

}
