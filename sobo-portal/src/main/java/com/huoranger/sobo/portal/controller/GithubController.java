package com.huoranger.sobo.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.github.GithubOauthLoginRequest;
import com.huoranger.sobo.api.service.GithubApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.common.support.LogUtil;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author huoranger
 * @create 2021/5/15
 * @desc
 **/
@Slf4j
@Controller
@RequestMapping("/github")
public class GithubController {

    @Resource
    private GithubApiService githubApiService;

    @GetMapping("/oauth-callback")
    public String index(GithubOauthLoginRequest request, HttpServletRequest servletRequest, HttpServletResponse response) {
        LogUtil.info(log, "github OauthRequest = {}", request);
        request.setIp(WebUtil.requestIp(servletRequest));
        request.setUa(WebUtil.requestUa(servletRequest));

        ResultModel<String> resultModel = githubApiService.oauthLogin(request);
        if (!resultModel.getSuccess()) {
            return "redirect:/?toast=" + resultModel.getMessage();
        }

        WebUtil.cookieAddSid(response, resultModel.getData());
        return "redirect:/?" + Constant.REQUEST_QUERY_TOKEN_KEY + "=" + resultModel.getData();
    }
}
