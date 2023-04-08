package com.huoranger.sobo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.service.SearchApiService;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.portal.request.SearchRequest;
import com.huoranger.sobo.common.support.GlobalViewConfig;
import com.huoranger.sobo.portal.support.WebConst;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Controller
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchApiService searchApiService;

    @Resource
    private WebUtil webUtil;

    @Resource
    private GlobalViewConfig globalViewConfig;

    @GetMapping
    public String index(SearchRequest searchRequest, Model model) {
        model.addAttribute("key", searchRequest.getKey());

        ResultModel<PageResponseModel<PostsVO>> pageResponseModelResultModel = searchPostsList(searchRequest);
        if (pageResponseModelResultModel.getSuccess() && !ObjectUtils.isEmpty(pageResponseModelResultModel.getData())) {
            PageResponseModel<PostsVO> pageResponseModel = pageResponseModelResultModel.getData();

            model.addAttribute("postsList", webUtil.buildPostsList(pageResponseModel.getList()));
            model.addAttribute("pager", pager(searchRequest, pageResponseModel));
        } else {
            model.addAttribute("postsList", webUtil.buildArticles(new ArrayList<>()));

            PageResponseModel pageResponseModel = new PageResponseModel();
            pageResponseModel.setTotal(0L);
            model.addAttribute("pager", pager(searchRequest, pageResponseModel));
        }

        return "search";
    }

    private ResultModel<PageResponseModel<PostsVO>> searchPostsList(SearchRequest searchRequest) {
        PageRequestModel<String> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setFilter(searchRequest.getKey());
        pageRequestModel.setPageSize(globalViewConfig.getPageSize());
        pageRequestModel.setPageNo(searchRequest.getPageNo());

        return searchApiService.pagePostsSearch(pageRequestModel);
    }

    private Map<String, Object> pager(SearchRequest searchRequest, PageResponseModel pageResponseModel) {
        String queryPath = "?key=" + searchRequest.getKey() + "&" + WebConst.PAGE_NO_NAME + "=";
        return webUtil.buildPager(searchRequest.getPageNo(), queryPath, pageResponseModel);
    }

}
