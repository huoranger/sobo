package com.huoranger.sobo.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.article.ArticleAuthorPageRequest;
import com.huoranger.sobo.api.request.faq.FaqAuthorPageRequest;
import com.huoranger.sobo.api.request.user.UserTokenLogoutRequest;
import com.huoranger.sobo.api.response.article.ArticleUserPageResponse;
import com.huoranger.sobo.api.response.faq.FaqUserPageResponse;
import com.huoranger.sobo.api.response.user.UserInfoResponse;
import com.huoranger.sobo.api.response.user.UserPageResponse;
import com.huoranger.sobo.api.service.ArticleApiService;
import com.huoranger.sobo.api.service.FaqApiService;
import com.huoranger.sobo.api.service.UserApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.common.support.GlobalViewConfig;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.portal.request.UserRequest;
import com.huoranger.sobo.portal.support.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;

/**
 * @author huoranger
 **/
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserApiService userApiService;

    @Resource
    private ArticleApiService articleApiService;

    @Resource
    private FaqApiService faqApiService;

    @Resource
    private WebUtil webUtil;

    @Resource
    private GlobalViewConfig globalViewConfig;

    private static final String DOMAIN_ARTICLE_NAME = "文章";
    private static final String DOMAIN_FAQ_NAME = "问答";

    /**
     * 用户主页
     * @param uid
     * @param userRequest
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/{uid}")
    public String index(@PathVariable("uid") Long uid, UserRequest userRequest, HttpServletRequest request, Model model) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil. (request));
        // 设置页面类型（文章或者问答）
        userRequest.setType(ObjectUtils.isEmpty(userRequest.getType()) ? DOMAIN_ARTICLE_NAME : userRequest.getType());

        if (DOMAIN_ARTICLE_NAME.equals(userRequest.getType())) {
            articles(uid, userRequest, model);
        } else if (DOMAIN_FAQ_NAME.equals(userRequest.getType())) {
            faqs(uid, userRequest, model);
        } else {
            throw ViewException.build("类别不存在");
        }

        ResultModel<UserInfoResponse> resultModel = userApiService.info(uid);
        if (!resultModel.getSuccess() || ObjectUtils.isEmpty(resultModel.getData())) {
            throw ViewException.build(resultModel.getMessage());
        }

        UserInfoResponse userInfoResponse = resultModel.getData();
        model.addAttribute("user", userInfoResponse)
             .addAttribute("hasFollow", hasFollow(userInfoResponse.getId()))
             .addAttribute("typeList", typeList(userRequest))
             .addAttribute("postsList", postsList())
             .addAttribute("fansList", pageUser(uid, (requestModel) -> userApiService.pageFans(requestModel)))
             .addAttribute("followerList", pageUser(uid, (requestModel) -> userApiService.pageFollower(requestModel)));
        return "user";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        String page = WebConst.REQUEST_REDIRECT_PREFIX + "/";

        String sid = WebContext.getCurrentSid();
        if (ObjectUtils.isEmpty(sid)) {
            return page;
        }

        // 登出
        UserTokenLogoutRequest logoutRequest = UserTokenLogoutRequest.builder()
                .token(sid)
                .build();
        // 设置用户访问环境，记录日志
        logoutRequest.setIp(WebUtil.requestIp(request));
        logoutRequest.setUa(WebUtil.requestUa(request));
        userApiService.logout(logoutRequest);

        // 删除 cookie 中登录凭证
        WebUtil.cookieDelSid(response);

        return page;
    }

    private boolean hasFollow(Long followed) {
        ResultModel<Boolean> resultModel = userApiService.hasFollow(followed);
        if (!resultModel.getSuccess()) {
            return false;
        }
        return resultModel.getData();
    }

    /**
     * 问答
     * @param uid
     * @param request
     * @param model
     */
    private void faqs(Long uid, UserRequest request, Model model) {
        PageRequestModel<FaqAuthorPageRequest> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageNo(request.getPageNo());
        pageRequestModel.setPageSize(globalViewConfig.getPageSize());
        pageRequestModel.setFilter(FaqAuthorPageRequest.builder()
                .userId(uid)
                .build());
        ResultModel<PageResponseModel<FaqUserPageResponse>> resultModel = faqApiService.authorPage(pageRequestModel);
        if (resultModel.getSuccess() && !ObjectUtils.isEmpty(resultModel.getData())) {
            PageResponseModel<FaqUserPageResponse> pageResponseModel = resultModel.getData();

            model.addAttribute("articleList", webUtil.buildFaqs(pageResponseModel.getList()));
            model.addAttribute("pager", pager(request, pageResponseModel));
        } else {
            PageResponseModel pageResponseModel = new PageResponseModel();
            pageResponseModel.setTotal(0L);

            model.addAttribute("articleList", webUtil.buildFaqs(new ArrayList<>()));
            model.addAttribute("pager", pager(request, pageResponseModel));
        }
    }

    /**
     * 文章
     * @param uid
     * @param request
     * @param model
     */
    private void articles(Long uid, UserRequest request, Model model) {
        PageRequestModel<ArticleAuthorPageRequest> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageNo(request.getPageNo());
        pageRequestModel.setPageSize(globalViewConfig.getPageSize());
        pageRequestModel.setFilter(ArticleAuthorPageRequest.builder()
                .userId(uid)
                .build());
        ResultModel<PageResponseModel<ArticleUserPageResponse>> resultModel = articleApiService.authorPage(pageRequestModel);
        if (resultModel.getSuccess() && !ObjectUtils.isEmpty(resultModel.getData())) {
            PageResponseModel<ArticleUserPageResponse> pageResponseModel = resultModel.getData();

            model.addAttribute("articleList", webUtil.buildArticles(pageResponseModel.getList()));
            model.addAttribute("pager", pager(request, pageResponseModel));
        } else {
            PageResponseModel pageResponseModel = new PageResponseModel();
            pageResponseModel.setTotal(0L);

            model.addAttribute("articleList", webUtil.buildArticles(new ArrayList<>()));
            model.addAttribute("pager", pager(request, pageResponseModel));
        }
    }

    private List<Map<String, Object>> pageUser(Long uid, Function<PageRequestModel<Long>, ResultModel<PageResponseModel<UserPageResponse>>> function) {
        List<Map<String, Object>> userList = new ArrayList<>();

        PageRequestModel<Long> pageRequestModel = new PageRequestModel<>();
        pageRequestModel.setPageNo(1).setPageSize(12).setFilter(uid);

        ResultModel<PageResponseModel<UserPageResponse>> resultModel = function.apply(pageRequestModel);
        if (!resultModel.getSuccess()) {
            return userList;
        }
        PageResponseModel<UserPageResponse> pageResponsePageResponseModel = resultModel.getData();
        SafesUtil.ofList(pageResponsePageResponseModel.getList()).forEach(userPageResponse -> {
            Map<String, Object> user = new HashMap<>();
            user.put("id", userPageResponse.getId());
            user.put("name", userPageResponse.getNickname());
            user.put("headImg", userPageResponse.getAvatar());
            userList.add(user);
        });

        return userList;
    }

    private List<Map<String, Object>> typeList(UserRequest request) {
        List<Map<String, Object>> typeList = new ArrayList<>();
        Map<String, Object> article = new HashMap<>();
        article.put("name", DOMAIN_ARTICLE_NAME);
        article.put("selected", request.getType().equals(DOMAIN_ARTICLE_NAME));
        typeList.add(article);

        Map<String, Object> faq = new HashMap<>();
        faq.put("name", DOMAIN_FAQ_NAME);
        faq.put("selected", request.getType().equals(DOMAIN_FAQ_NAME));
        typeList.add(faq);

        return typeList;
    }

    private List<Map<String, Object>> postsList() {
        List<Map<String, Object>> postsList = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            Map<String, Object> posts = new HashMap<>();
            posts.put("id", i);
            posts.put("title", "这里是mock的测试问题标题，后期需要从后端接口查询真实数据" + i);
            posts.put("createdAt", new Date());
            postsList.add(posts);
        }

        return postsList;
    }

    private Map<String, Object> pager(UserRequest request, PageResponseModel pageResponseModel) {
        String queryPath = "?type=" + request.getType() + "&" + WebConst.PAGE_NO_NAME + "=";

        return webUtil.buildPager(request.getPageNo(), queryPath, pageResponseModel);
    }


}
