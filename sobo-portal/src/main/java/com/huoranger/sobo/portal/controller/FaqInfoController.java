package com.huoranger.sobo.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.faq.FaqAuthorPageRequest;
import com.huoranger.sobo.api.response.comment.CommentPageResponse;
import com.huoranger.sobo.api.response.faq.FaqInfoResponse;
import com.huoranger.sobo.api.response.faq.FaqUserPageResponse;
import com.huoranger.sobo.api.service.ApprovalApiService;
import com.huoranger.sobo.api.service.CommentApiService;
import com.huoranger.sobo.api.service.FaqApiService;
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
 **/
@Slf4j
@Controller
@RequestMapping("/faq")
public class FaqInfoController {

    @Resource
    private CommentApiService commentApiService;

    @Resource
    private FaqApiService faqApiService;

    @Resource
    private UserApiService userApiService;

    @Resource
    private ApprovalApiService approvalApiService;

    @Resource
    private WebUtil webUtil;

    @Resource
    private GlobalViewConfig globalViewConfig;

    @GetMapping("/{id}")
    public String home(@PathVariable("id") Long id, BasePageRequest pageRequest, Model model, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebContext.getCurrentSid());

        ResultModel<FaqInfoResponse> resultModel = faqApiService.info(id);
        if (!resultModel.getSuccess() || ObjectUtils.isEmpty(resultModel)) {
            throw ViewException.build(resultModel.getMessage());
        }
        FaqInfoResponse faqInfoResponse = resultModel.getData();

        model.addAttribute("posts", webUtil.buildPostsInfo(faqInfoResponse));
        model.addAttribute("hasFollow", hasFollow(faqInfoResponse.getAuthorId()));
        model.addAttribute("hasApproval", hasApproval(id));
        model.addAttribute("authorFaqList", authorFaqList(faqInfoResponse.getAuthorId()));

        PageResponseModel pageResponseModel = pickComment(model, id, pageRequest);
        model.addAttribute("pager", pager(pageRequest.getPageNo(), pageResponseModel));

        Set<Long> tagIds = SafesUtil.ofList(faqInfoResponse.getTags()).stream().map(TagVO::getId).collect(Collectors.toSet());
        model.addAttribute("relatedPosts", webUtil.relatedPosts(tagIds));
        model.addAttribute("usedTags", webUtil.usedTags());

        return "faq-info";
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

    private List<Map<String, Object>> authorFaqList(Long authorId) {
        List<Map<String, Object>> postsList = new ArrayList<>();

        PageRequestModel<FaqAuthorPageRequest> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageNo(1);
        pageRequestModel.setPageSize(10);
        pageRequestModel.setFilter(FaqAuthorPageRequest.builder()
                .userId(authorId)
                .build());
        ResultModel<PageResponseModel<FaqUserPageResponse>> resultModel = faqApiService.authorPage(pageRequestModel);
        if (!resultModel.getSuccess()) {
            return postsList;
        }

        PageResponseModel<FaqUserPageResponse> pageResponseModel = resultModel.getData();
        SafesUtil.ofList(pageResponseModel.getList()).forEach(faqUserPageResponse -> {
            Map<String, Object> posts = new HashMap<>();
            posts.put("id", faqUserPageResponse.getId());
            posts.put("title", faqUserPageResponse.getTitle());
            posts.put("createdAt", faqUserPageResponse.getCreateAt());
            postsList.add(posts);
        });

        return postsList;
    }

}
