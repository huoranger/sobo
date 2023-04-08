package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.tag.TagCreateRequest;
import com.huoranger.sobo.api.request.tag.TagPageRequest;
import com.huoranger.sobo.api.response.tag.TagPageResponse;
import com.huoranger.sobo.api.response.tag.TagQueryResponse;
import com.huoranger.sobo.api.service.TagApiService;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.app.manager.TagManager;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.ArticleValidator;
import com.huoranger.sobo.facade.validator.PageRequestModelValidator;
import com.huoranger.sobo.facade.validator.TagValidator;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
@Service
public class TagApiServiceImpl implements TagApiService {

    @Resource
    private TagManager tagManager;

    @Override
    public ResultModel<List<TagQueryResponse>> queryAllRef() {
        return ResultModelUtil.success(tagManager.queryAllRef());
    }

    @Override
    public ResultModel create(TagCreateRequest request) {
        TagValidator.create(request);

        tagManager.create(request);

        return ResultModelUtil.success();
    }

    @Override
    public ResultModel<TagQueryResponse> getByName(String name) {
        return ResultModelUtil.success(tagManager.getByName(name));
    }

    @Override
    public ResultModel<List<TagQueryResponse>> queryAll() {
        return ResultModelUtil.success(tagManager.queryAll());
    }

    @Override
    public ResultModel<List<TagQueryResponse>> queryInIds(Set<Long> ids) {
        CheckUtil.checkParamToast(ids, "ids");

        return ResultModelUtil.success(tagManager.queryInIds(ids));
    }

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePosts(PageRequestModel<Long> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(tagManager.pagePosts(pageRequestModel));
    }

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePostsByTagIds(PageRequestModel<Set<Long>> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(tagManager.pagePostsByTagIds(pageRequestModel));
    }

    @Override
    public ResultModel<PageResponseModel<TagPageResponse>> page(PageRequestModel<TagPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(tagManager.page(pageRequestModel));
    }

    @Override
    public ResultModel auditState(AdminBooleanRequest booleanRequest) {
        ArticleValidator.validatorBooleanRequest(booleanRequest);

        tagManager.tagAuditState(booleanRequest);

        return ResultModelUtil.success();
    }
}
