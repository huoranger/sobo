package com.huoranger.sobo.portal.controller.admin;

import com.huoranger.sobo.portal.support.WebUtil;
import org.springframework.web.bind.annotation.*;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.user.UserAdminPageRequest;
import com.huoranger.sobo.api.request.user.UserOptLogPageRequest;
import com.huoranger.sobo.api.response.user.UserOptLogPageResponse;
import com.huoranger.sobo.api.response.user.UserPageResponse;
import com.huoranger.sobo.api.service.UserApiService;
import com.huoranger.sobo.common.constant.Constant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/12/8
 * @desc
 **/
@RestController
@RequestMapping("/admin-rest/user")
public class AdminRestUserController {

    @Resource
    private UserApiService userApiService;

    @PostMapping("/page")
    public ResultModel<PageResponseModel<UserPageResponse>> add(@RequestBody PageRequestModel<UserAdminPageRequest> pageRequestModel
            , HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.adminPage(pageRequestModel);
    }

    @PostMapping("/enable/{uid}")
    public ResultModel enable(@PathVariable Long uid, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.enable(uid);
    }

    @PostMapping("/disable/{uid}")
    public ResultModel disable(@PathVariable Long uid, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.disable(uid);
    }

    @PostMapping("/page-opt-log")
    public ResultModel<PageResponseModel<UserOptLogPageResponse>> pageOptLog(@RequestBody PageRequestModel<UserOptLogPageRequest> pageRequestModel
            , HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.pageOptLog(pageRequestModel);
    }

    @PostMapping("/update-role")
    public ResultModel updateRole(@RequestBody AdminBooleanRequest booleanRequest
            , HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return userApiService.updateRole(booleanRequest);
    }
}
