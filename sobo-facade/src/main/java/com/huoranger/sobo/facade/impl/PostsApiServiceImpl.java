package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.service.PostsApiService;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.app.manager.PostsManager;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.ArticleValidator;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/11/25
 * @desc
 **/
@Service
public class PostsApiServiceImpl implements PostsApiService {

    @Resource
    private PostsManager postsManager;

    @Override
    public ResultModel delete(Long id) {
        postsManager.delete(id);

        return ResultModelUtil.success();
    }

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePostsFood(PageRequestModel pageRequestModel) {
        return ResultModelUtil.success(postsManager.pagePostsFood(pageRequestModel));
    }

    @Override
    public ResultModel auditState(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);

        postsManager.auditState(booleanRequest);

        return ResultModelUtil.success();
    }

}
