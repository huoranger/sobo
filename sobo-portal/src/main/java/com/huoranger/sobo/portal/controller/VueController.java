package com.huoranger.sobo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.response.user.UserInfoResponse;
import com.huoranger.sobo.api.service.UserApiService;
import com.huoranger.sobo.common.enums.UserRoleEn;
import com.huoranger.sobo.portal.support.ViewException;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @desc 管理后台和编辑器页面路由
 **/
@Controller
@RequestMapping("/")
public class VueController {

    @Resource
    private UserApiService userApiService;

    @GetMapping(value = {"/article/editor", "/faq/editor", "/faq/editor/{id}", "/article/editor/{id}"})
    public String editor() {
        return "vue-index";
    }

    @GetMapping(value = {"/admin"
            , "/admin/user-manager", "/admin/user-opt-log"
            , "/admin/article-manager", "/admin/article-type"
            , "/admin/faq-manager"
            , "/admin/message-manager"
            , "/admin/tag-manager"})
    public String admin(HttpServletRequest request) {
        String sid = WebUtil.cookieGetSid(request);
        if (ObjectUtils.isEmpty(sid)) {
            throw ViewException.build("用户未登录");
        }

        ResultModel<UserInfoResponse> resultModel = userApiService.info(sid);
        if (!resultModel.getSuccess() || ObjectUtils.isEmpty(resultModel.getData())) {
            throw ViewException.build(resultModel.getMessage());
        }
        UserInfoResponse userInfoResponse = resultModel.getData();
        if (UserRoleEn.USER.getValue().equals(userInfoResponse.getRole())) {
            throw ViewException.build("无权限访问");
        }

        return "vue-admin";
    }

}
