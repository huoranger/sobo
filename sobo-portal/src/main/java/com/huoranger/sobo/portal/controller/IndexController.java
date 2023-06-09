package com.huoranger.sobo.portal.controller;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.article.ArticleUserPageRequest;
import com.huoranger.sobo.api.response.article.ArticleQueryTypesResponse;
import com.huoranger.sobo.api.response.article.ArticleUserPageResponse;
import com.huoranger.sobo.api.response.config.ConfigResponse;
import com.huoranger.sobo.api.response.faq.FaqHotsResponse;
import com.huoranger.sobo.api.service.ArticleApiService;
import com.huoranger.sobo.api.service.ConfigApiService;
import com.huoranger.sobo.api.service.FaqApiService;
import com.huoranger.sobo.common.enums.ConfigTypeEn;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.common.support.GlobalViewConfig;
import com.huoranger.sobo.portal.support.WebConst;
import com.huoranger.sobo.portal.request.IndexRequest;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author huoranger
 * @create 2020/9/15
 * @desc
 **/
@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

    private static final String ALL_TYPE_NAME = "全部文章";

    @Resource
    private ArticleApiService articleApiService;

    @Value("${custom-config.view.index-page.sidebar-type-names}")
    private String sideBarTypeNames;

    @Resource
    private FaqApiService faqApiService;

    @Resource
    private WebUtil webUtil;

    @Resource
    private GlobalViewConfig globalViewConfig;

    @Resource
    private ConfigApiService configApiService;

    @GetMapping
    public String index(IndexRequest request, Model model, HttpServletRequest servletRequest, HttpServletResponse response) {
        if (!ObjectUtils.isEmpty(request.getToken())) {
            WebUtil.cookieAddSid(response, request.getToken());
        }

        request.setType(ObjectUtils.isEmpty(request.getType()) ? ALL_TYPE_NAME : request.getType());

        model.addAttribute("currentDomain", WebConst.DOMAIN_ARTICLE);
        model.addAttribute("toast", request.getToast());
        model.addAttribute("token", request.getToken());

        ResultModel<List<ConfigResponse>> configResult = configApiService.queryAvailable(Sets.newHashSet(ConfigTypeEn.HOME_CAROUSEL.getValue()
                , ConfigTypeEn.SIDEBAR_CAROUSEL.getValue()));
        // 获取轮播图
        if (configResult.getSuccess() && !ObjectUtils.isEmpty(configResult.getData())) {
            model.addAttribute("homeCarouselList", webUtil.carouselList(configResult.getData(), ConfigTypeEn.HOME_CAROUSEL));
            model.addAttribute("sideCarouselList", webUtil.carouselList(configResult.getData(), ConfigTypeEn.SIDEBAR_CAROUSEL));
        } else {
            model.addAttribute("homeCarouselList", new ArrayList<>());
            model.addAttribute("sideCarouselList", new ArrayList<>());
        }

        model.addAttribute("sideBarTypes", sideBarTypes());
        model.addAttribute("hotFaqList", hotFaqList());
        model.addAttribute("typeList", typeList(request));
        model.addAttribute("usedTags", webUtil.usedTags());

        // 文章列表请求参数
        ResultModel<PageResponseModel<ArticleUserPageResponse>> resultModel = userPage(request);
        if (resultModel.getSuccess() && !ObjectUtils.isEmpty(resultModel.getData())) {
            PageResponseModel<ArticleUserPageResponse> pageResponseModel = resultModel.getData();

            model.addAttribute("articleList", webUtil.buildArticles(pageResponseModel.getList()));
            model.addAttribute("pager", pager(request, pageResponseModel));
        } else {
            model.addAttribute("articleList", webUtil.buildArticles(new ArrayList<>()));

            PageResponseModel pageResponseModel = new PageResponseModel();
            pageResponseModel.setTotal(0L);
            model.addAttribute("pager", pager(request, pageResponseModel));
        }

        return "index";
    }

    private ResultModel<PageResponseModel<ArticleUserPageResponse>> userPage(IndexRequest request) {
        PageRequestModel<ArticleUserPageRequest> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageNo(request.getPageNo());
        pageRequestModel.setPageSize(globalViewConfig.getPageSize());
        pageRequestModel.setFilter(ArticleUserPageRequest.builder()
                .typeName(ALL_TYPE_NAME.equals(request.getType()) ? null : request.getType())
                .build());
        return articleApiService.userPage(pageRequestModel);
    }

    private Map<String, Object> pager(IndexRequest request, PageResponseModel pageResponseModel) {
        String queryPath = "?type=" + request.getType() + "&" + WebConst.PAGE_NO_NAME + "=";
        return webUtil.buildPager(request.getPageNo(), queryPath, pageResponseModel);
    }

    private List<Map<String, Object>> hotFaqList() {
        List<Map<String, Object>> postsList = new ArrayList<>();

        ResultModel<List<FaqHotsResponse>> resultModel = faqApiService.hots(10);
        if (!resultModel.getSuccess()) {
            return postsList;
        }

        SafesUtil.ofList(resultModel.getData()).forEach(faqHotsResponse -> {
            Map<String, Object> posts = new HashMap<>();
            posts.put("id", faqHotsResponse.getId());
            posts.put("title", faqHotsResponse.getTitle());
            posts.put("createdAt", faqHotsResponse.getCreateAt());
            postsList.add(posts);
        });

        return postsList;
    }

    private List<Map<String, Object>> sideBarTypes() {
        List<Map<String, Object>> res = new ArrayList<>();

        if (ObjectUtils.isEmpty(sideBarTypeNames)) {
            return res;
        }

        String[] types = sideBarTypeNames.split(",");
        for (String typeName : types) {
            if (ObjectUtils.isEmpty(typeName)) {
                continue;
            }
            Map<String, Object> type = new HashMap<>();
            type.put("name", typeName);
            type.put("postsList", typeList(typeName));
            res.add(type);
        }

        return res;
    }

    private List<Map<String, Object>> typeList(String typeName) {
        List<Map<String, Object>> postsList = new ArrayList<>();
        if (ObjectUtils.isEmpty(typeName)) {
            return postsList;
        }

        PageRequestModel<ArticleUserPageRequest> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageNo(1);
        pageRequestModel.setPageSize(10);
        pageRequestModel.setFilter(ArticleUserPageRequest.builder()
                .typeName(typeName)
                .build());
        ResultModel<PageResponseModel<ArticleUserPageResponse>> resultModel = articleApiService.userPage(pageRequestModel);
        if (!resultModel.getSuccess()) {
            return postsList;
        }

        PageResponseModel<ArticleUserPageResponse> pageResponseModel = resultModel.getData();
        SafesUtil.ofList(pageResponseModel.getList()).forEach(articleUserPageResponse -> {
            Map<String, Object> posts = new HashMap<>();
            posts.put("id", articleUserPageResponse.getId());
            posts.put("title", articleUserPageResponse.getTitle());
            posts.put("createdAt", articleUserPageResponse.getCreateAt());
            postsList.add(posts);
        });

        return postsList;
    }

    private List<Map<String, Object>> typeList(IndexRequest request) {
        List<Map<String, Object>> typeList = new ArrayList<>();
        Long allRefCount = 0L;

        Map<String, Object> all = new HashMap<>();
        all.put("name", ALL_TYPE_NAME);
        all.put("selected", request.getType().equals(ALL_TYPE_NAME));
        typeList.add(all);

        ResultModel<List<ArticleQueryTypesResponse>> resultModel = articleApiService.queryAllTypes();
        if (resultModel.getSuccess() && !ObjectUtils.isEmpty(resultModel.getData())) {
            List<ArticleQueryTypesResponse> responses = resultModel.getData();
            for (ArticleQueryTypesResponse response : responses) {
                if (response.getRefCount().equals(0L)) {
                    continue;
                }

                allRefCount += response.getRefCount();

                Map<String, Object> type = new HashMap<>();
                type.put("name", response.getName());
                type.put("refCount", response.getRefCount());
                type.put("selected", request.getType().equals(response.getName()));
                typeList.add(type);
            }
        }

        all.put("refCount", allRefCount);

        return typeList;
    }
}
