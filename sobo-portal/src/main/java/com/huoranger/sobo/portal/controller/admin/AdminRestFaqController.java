package com.huoranger.sobo.portal.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.faq.FaqAdminPageRequest;
import com.huoranger.sobo.api.response.faq.FaqUserPageResponse;
import com.huoranger.sobo.api.service.FaqApiService;
import com.huoranger.sobo.api.service.PostsApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/10/27
 * @desc
 **/
@RestController
@RequestMapping("/admin-rest/faq")
public class AdminRestFaqController {

    @Resource
    private FaqApiService faqApiService;

    @Resource
    private PostsApiService postsApiService;

    @PostMapping("/page")
    public ResultModel<PageResponseModel<FaqUserPageResponse>> page(@RequestBody PageRequestModel<FaqAdminPageRequest> pageRequestModel
            , HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return faqApiService.adminPage(pageRequestModel);
    }

    @PostMapping("/audit-state")
    public ResultModel auditState(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return postsApiService.auditState(booleanRequest);
    }

}
