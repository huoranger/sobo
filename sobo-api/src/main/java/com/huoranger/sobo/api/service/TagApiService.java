package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.tag.TagCreateRequest;
import com.huoranger.sobo.api.request.tag.TagPageRequest;
import com.huoranger.sobo.api.response.tag.TagPageResponse;
import com.huoranger.sobo.api.response.tag.TagQueryResponse;
import com.huoranger.sobo.api.vo.PostsVO;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
public interface TagApiService {

    ResultModel<List<TagQueryResponse>> queryAllRef();

    ResultModel create(TagCreateRequest request);

    ResultModel<TagQueryResponse> getByName(String name);

    ResultModel<List<TagQueryResponse>> queryAll();

    ResultModel<List<TagQueryResponse>> queryInIds(Set<Long> ids);

    ResultModel<PageResponseModel<PostsVO>> pagePosts(PageRequestModel<Long> pageRequestModel);

    ResultModel<PageResponseModel<PostsVO>> pagePostsByTagIds(PageRequestModel<Set<Long>> pageRequestModel);

    ResultModel<PageResponseModel<TagPageResponse>> page(PageRequestModel<TagPageRequest> pageRequestModel);

    ResultModel auditState(AdminBooleanRequest booleanRequest);
}
