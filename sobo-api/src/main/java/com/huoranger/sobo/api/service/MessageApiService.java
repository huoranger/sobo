package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.message.MessagePageRequest;
import com.huoranger.sobo.api.response.message.MessagePageResponse;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
public interface MessageApiService {

    ResultModel<PageResponseModel<MessagePageResponse>> page(PageRequestModel<MessagePageRequest> request);

    ResultModel markIsRead(Long messageId);

    ResultModel<Long> countUnRead();

}
