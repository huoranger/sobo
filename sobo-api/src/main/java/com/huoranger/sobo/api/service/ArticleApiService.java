package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.article.*;
import com.huoranger.sobo.api.response.article.ArticleInfoResponse;
import com.huoranger.sobo.api.response.article.ArticleQueryTypesResponse;
import com.huoranger.sobo.api.response.article.ArticleUserPageResponse;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
public interface ArticleApiService {

    ResultModel<List<ArticleQueryTypesResponse>> queryAllTypes();

    ResultModel<List<ArticleQueryTypesResponse>> queryAdminTypes();

    ResultModel<PageResponseModel<ArticleUserPageResponse>> adminPage(PageRequestModel<ArticleAdminPageRequest> pageRequestModel);

    ResultModel<List<ArticleQueryTypesResponse>> queryEditArticleTypes();

    ResultModel addType(ArticleAddTypeRequest request);

    ResultModel<Long> saveArticle(ArticleSaveArticleRequest request);

    ResultModel<PageResponseModel<ArticleUserPageResponse>> userPage(PageRequestModel<ArticleUserPageRequest> pageRequestModel);

    ResultModel<PageResponseModel<ArticleUserPageResponse>> authorPage(PageRequestModel<ArticleAuthorPageRequest> pageRequestModel);

    ResultModel<ArticleInfoResponse> info(Long id);

    ResultModel adminTop(AdminBooleanRequest booleanRequest);

    ResultModel adminOfficial(AdminBooleanRequest booleanRequest);

    ResultModel adminMarrow(AdminBooleanRequest booleanRequest);

    ResultModel<PageResponseModel<ArticleQueryTypesResponse>> typePage(PageRequestModel<ArticleAdminTypePageRequest> pageRequestModel);

    ResultModel typeAuditState(AdminBooleanRequest booleanRequest);
}
