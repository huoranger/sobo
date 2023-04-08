package com.huoranger.sobo.facade.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.message.MessagePageRequest;
import com.huoranger.sobo.api.response.message.MessagePageResponse;
import com.huoranger.sobo.api.service.MessageApiService;
import com.huoranger.sobo.app.manager.MessageManager;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.MessageValidator;
import com.huoranger.sobo.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
@Service
public class MessageApiServiceImpl implements MessageApiService {

    @Resource
    private MessageManager messageManager;

    @Override
    public ResultModel<PageResponseModel<MessagePageResponse>> page(PageRequestModel<MessagePageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);
        pageRequestModel.setFilter(JSON.parseObject(JSON.toJSONString(pageRequestModel.getFilter()), MessagePageRequest.class));
        MessageValidator.page(pageRequestModel.getFilter());

        return ResultModelUtil.success(messageManager.page(pageRequestModel));
    }

    @Override
    public ResultModel markIsRead(Long messageId) {
        messageManager.markIsRead(messageId);

        return ResultModelUtil.success();
    }

    @Override
    public ResultModel<Long> countUnRead() {
        return ResultModelUtil.success(messageManager.countUnRead());
    }
}
