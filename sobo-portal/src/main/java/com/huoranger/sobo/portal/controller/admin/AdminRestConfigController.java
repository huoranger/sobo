package com.huoranger.sobo.portal.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.config.ConfigAddRequest;
import com.huoranger.sobo.api.request.config.ConfigPageRequest;
import com.huoranger.sobo.api.request.config.ConfigUpdateRequest;
import com.huoranger.sobo.api.response.config.ConfigResponse;
import com.huoranger.sobo.api.service.ConfigApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/12/26
 * @desc
 **/
@RestController
@RequestMapping("/admin-rest/config")
public class AdminRestConfigController {

    @Resource
    private ConfigApiService configApiService;

    @PostMapping("/add")
    public ResultModel add(@RequestBody ConfigAddRequest addRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return configApiService.add(addRequest);
    }

    @PostMapping("/update")
    public ResultModel update(@RequestBody ConfigUpdateRequest updateRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return configApiService.update(updateRequest);
    }

    @PostMapping("/state")
    public ResultModel state(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return configApiService.state(booleanRequest);
    }

    @PostMapping("/page")
    public ResultModel<PageResponseModel<ConfigResponse>> page(@RequestBody PageRequestModel<ConfigPageRequest> pageRequestModel, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return configApiService.page(pageRequestModel);
    }

}
