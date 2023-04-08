package com.huoranger.sobo.portal.controller.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.service.MessageApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
@RestController
@RequestMapping("/message-rest")
public class MessageRestController {

    @Resource
    private MessageApiService messageApiService;

    @PostMapping("/mark-is-read/{id}")
    public ResultModel delete(@PathVariable("id") Long id, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return messageApiService.markIsRead(id);
    }


}