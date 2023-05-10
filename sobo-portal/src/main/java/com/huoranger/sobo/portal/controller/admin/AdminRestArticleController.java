package com.huoranger.sobo.portal.controller.admin;

import org.springframework.web.bind.annotation.*;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.article.ArticleAddTypeRequest;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.article.ArticleAdminPageRequest;
import com.huoranger.sobo.api.request.article.ArticleAdminTypePageRequest;
import com.huoranger.sobo.api.response.article.ArticleQueryTypesResponse;
import com.huoranger.sobo.api.response.article.ArticleUserPageResponse;
import com.huoranger.sobo.api.service.ArticleApiService;
import com.huoranger.sobo.api.service.PostsApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
@RestController
@RequestMapping("/admin-rest/article")
public class AdminRestArticleController {

    @Resource
    private ArticleApiService articleApiService;

    @Resource
    private PostsApiService postsApiService;

    @PostMapping("/all-type")
    public ResultModel<List<ArticleQueryTypesResponse>> allAdminTypes(HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.queryAdminTypes();
    }

    @PostMapping("/page")
    public ResultModel<PageResponseModel<ArticleUserPageResponse>> page(@RequestBody PageRequestModel<ArticleAdminPageRequest> pageRequestModel
            , HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.adminPage(pageRequestModel);
    }

    @PostMapping("/top")
    public ResultModel top(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.adminTop(booleanRequest);
    }

    @PostMapping("/official")
    public ResultModel official(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.adminOfficial(booleanRequest);
    }

    @PostMapping("/marrow")
    public ResultModel marrow(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.adminMarrow(booleanRequest);
    }

    @PostMapping("/audit-state")
    public ResultModel auditState(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return postsApiService.auditState(booleanRequest);
    }

    /**
     * 类别分页接口
     * @param pageRequestModel
     * @param request
     * @return
     */
    @PostMapping("/type-page")
    public ResultModel<PageResponseModel<ArticleQueryTypesResponse>> typePage(@RequestBody PageRequestModel<ArticleAdminTypePageRequest> pageRequestModel, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.typePage(pageRequestModel);
    }

    @PostMapping("/type-audit-state")
    public ResultModel typeAuditState(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.typeAuditState(booleanRequest);
    }

    @PostMapping("/type-add")
    public ResultModel addType(@RequestBody ArticleAddTypeRequest articleAddTypeRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.addType(articleAddTypeRequest);
    }

}
