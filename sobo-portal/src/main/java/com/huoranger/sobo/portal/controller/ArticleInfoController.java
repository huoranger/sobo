package com.huoranger.sobo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.article.ArticleAuthorPageRequest;
import com.huoranger.sobo.api.response.article.ArticleInfoResponse;
import com.huoranger.sobo.api.response.article.ArticleUserPageResponse;
import com.huoranger.sobo.api.response.comment.CommentPageResponse;
import com.huoranger.sobo.api.service.ApprovalApiService;
import com.huoranger.sobo.api.service.ArticleApiService;
import com.huoranger.sobo.api.service.CommentApiService;
import com.huoranger.sobo.api.service.UserApiService;
import com.huoranger.sobo.api.vo.TagVO;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.common.support.GlobalViewConfig;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.portal.request.BasePageRequest;
import com.huoranger.sobo.portal.support.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huoranger
 * @create 20/10/26
 * @desc
 **/
@Controller
@RequestMapping("/article")
public class ArticleInfoController {

    @Resource
    private ArticleApiService articleApiService;

    @Resource
    private CommentApiService commentApiService;

    @Resource
    private UserApiService userApiService;

    @Resource
    private ApprovalApiService approvalApiService;

    @Resource
    private WebUtil webUtil;

    @Resource
    private GlobalViewConfig globalViewConfig;

    @GetMapping("/{id}")
    public String home(@PathVariable("id") Long id, BasePageRequest pageRequest, HttpServletRequest request, Model model) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebContext.getCurrentSid());

        ResultModel<ArticleInfoResponse> resultModel = articleApiService.info(id);
        if (!resultModel.getSuccess() || ObjectUtils.isEmpty(resultModel)) {
            throw ViewException.build(resultModel.getMessage());
        }
        ArticleInfoResponse articleInfoResponse = resultModel.getData();

        Map<String, Object> postsInfo = webUtil.buildPostsInfo(articleInfoResponse);
        model.addAttribute("motto", userApiService.info((Long) postsInfo.get("authorId")).getData().getSignature());

        model.addAttribute("posts", postsInfo);
        model.addAttribute("hasFollow", hasFollow(articleInfoResponse.getAuthorId()));
        model.addAttribute("hasApproval", hasApproval(id));
        model.addAttribute("authArticleList", authArticleList(articleInfoResponse.getAuthorId()));

        PageResponseModel pageResponseModel = pickComment(model, id, pageRequest);
        model.addAttribute("pager", pager(pageRequest.getPageNo(), pageResponseModel));

        Set<Long> tagIds = SafesUtil.ofList(articleInfoResponse.getTags()).stream().map(TagVO::getId).collect(Collectors.toSet());
        model.addAttribute("relatedPosts", webUtil.relatedPosts(tagIds));
        model.addAttribute("usedTags", webUtil.usedTags());

        return "article-info";
    }

    private boolean hasApproval(Long postsId) {
        ResultModel<Boolean> resultModel = approvalApiService.hasApproval(postsId);
        if (resultModel.getSuccess() && !ObjectUtils.isEmpty(resultModel.getData())) {
            return resultModel.getData();
        }
        return false;
    }

    private boolean hasFollow(Long followed) {
        ResultModel<Boolean> resultModel = userApiService.hasFollow(followed);
        if (!resultModel.getSuccess()) {
            return false;
        }
        return resultModel.getData();
    }

    private PageResponseModel pickComment(Model model, Long id, BasePageRequest pageRequest) {
        PageRequestModel<Long> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageSize(globalViewConfig.getPageSize());
        pageRequestModel.setPageNo(pageRequest.getPageNo());
        pageRequestModel.setFilter(id);

        ResultModel<PageResponseModel<CommentPageResponse>> resultModel = commentApiService.page(pageRequestModel);
        if (!resultModel.getSuccess()) {
            throw ViewException.build(resultModel.getMessage());
        }

        PageResponseModel<CommentPageResponse> pageResponseModel = resultModel.getData();
        model.addAttribute("comments", pageResponseModel.getList());

        return pageResponseModel;
    }

    private Map<String, Object> pager(Integer pageNo, PageResponseModel pageResponseModel) {
        String queryPath = "?" + WebConst.PAGE_NO_NAME + "=";

        return webUtil.buildPager(pageNo, queryPath, pageResponseModel);
    }

    private List<Map<String, Object>> authArticleList(Long authorId) {
        List<Map<String, Object>> postsList = new ArrayList<>();

        PageRequestModel<ArticleAuthorPageRequest> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageSize(10);
        pageRequestModel.setPageNo(1);
        pageRequestModel.setFilter(ArticleAuthorPageRequest.builder()
                .userId(authorId)
                .build());
        ResultModel<PageResponseModel<ArticleUserPageResponse>> resultModel = articleApiService.authorPage(pageRequestModel);
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

}
