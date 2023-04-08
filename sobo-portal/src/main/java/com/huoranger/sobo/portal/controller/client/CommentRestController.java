package com.huoranger.sobo.portal.controller.client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.comment.CommentCreateRequest;
import com.huoranger.sobo.api.service.CommentApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/11/17
 * @desc
 **/
@RestController
@RequestMapping("/comment-rest")
public class CommentRestController {

    @Resource
    private CommentApiService commentApiService;

    @PostMapping("/create")
    public ResultModel create(@RequestBody CommentCreateRequest createRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return commentApiService.create(createRequest);
    }

}
