package com.huoranger.sobo.portal.controller.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.service.PostsApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/11/25
 * @desc
 **/
@RestController
@RequestMapping("/posts-rest")
public class PostsRestController {

    @Resource
    private PostsApiService postsApiService;

    @PostMapping("/delete/{id}")
    public ResultModel delete(@PathVariable("id") Long id, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return postsApiService.delete(id);
    }

}
