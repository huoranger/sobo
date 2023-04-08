package com.huoranger.sobo.portal.controller.client;

import org.springframework.web.bind.annotation.*;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.article.ArticleSaveArticleRequest;
import com.huoranger.sobo.api.response.article.ArticleInfoResponse;
import com.huoranger.sobo.api.response.article.ArticleQueryTypesResponse;
import com.huoranger.sobo.api.service.ArticleApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
@RestController
@RequestMapping("/article-rest")
public class ArticleRestController {

    @Resource
    private ArticleApiService articleApiService;

    @PostMapping("/save")
    public ResultModel<Long> save(@RequestBody ArticleSaveArticleRequest articleRequest, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.saveArticle(articleRequest);
    }

    @PostMapping("/{id}")
    public ResultModel<ArticleInfoResponse> get(@PathVariable("id") Long id, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));
        return articleApiService.info(id);
    }

    @PostMapping("/editArticleTypes")
    public ResultModel<List<ArticleQueryTypesResponse>> getAllType(HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return articleApiService.queryEditArticleTypes();
    }
}
