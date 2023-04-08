package com.huoranger.sobo.portal.controller.admin;

import com.huoranger.sobo.portal.support.WebUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.tag.TagCreateRequest;
import com.huoranger.sobo.api.request.tag.TagPageRequest;
import com.huoranger.sobo.api.response.tag.TagPageResponse;
import com.huoranger.sobo.api.service.TagApiService;
import com.huoranger.sobo.common.constant.Constant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
@RestController
@RequestMapping("/admin-rest/tag")
public class AdminRestTagController {

    @Resource
    private TagApiService tagApiService;

    @PostMapping("/page")
    public ResultModel<PageResponseModel<TagPageResponse>> page(@RequestBody PageRequestModel<TagPageRequest> pageRequestModel
            , HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return tagApiService.page(pageRequestModel);
    }

    @PostMapping("/audit-state")
    public ResultModel auditState(@RequestBody AdminBooleanRequest booleanRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return tagApiService.auditState(booleanRequest);
    }

    @PostMapping("/add")
    public ResultModel add(@RequestBody TagCreateRequest request) {
        return tagApiService.create(request);
    }

}
