package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.comment.CommentCreateRequest;
import com.huoranger.sobo.api.response.comment.CommentPageResponse;
import com.huoranger.sobo.api.service.CommentApiService;
import com.huoranger.sobo.app.manager.CommentManager;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.CommentValidator;
import com.huoranger.sobo.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/11/6
 * @desc
 **/
@Service
public class CommentApiServiceImpl implements CommentApiService {

    @Resource
    private CommentManager commentManager;

    @Override
    public ResultModel create(CommentCreateRequest request) {
        CommentValidator.create(request);

        commentManager.create(request);

        return ResultModelUtil.success();
    }

    @Override
    public ResultModel<PageResponseModel<CommentPageResponse>> page(PageRequestModel<Long> pageRequest) {
        PageRequestModelValidator.validator(pageRequest);

        return ResultModelUtil.success(commentManager.page(pageRequest));
    }
}
