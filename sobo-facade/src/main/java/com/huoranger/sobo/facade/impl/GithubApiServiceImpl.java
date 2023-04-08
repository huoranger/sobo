package com.huoranger.sobo.facade.impl;

import com.huoranger.sobo.facade.support.ResultModelUtil;
import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.github.GithubOauthLoginRequest;
import com.huoranger.sobo.api.service.GithubApiService;
import com.huoranger.sobo.app.manager.GithubManager;
import com.huoranger.sobo.common.support.CheckUtil;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2021/5/15
 * @desc
 **/
@Service
public class GithubApiServiceImpl implements GithubApiService {

    @Resource
    private GithubManager githubManager;

    @Override
    public ResultModel<String> oauthLogin(GithubOauthLoginRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getCode(), "code");

        return ResultModelUtil.success(githubManager.oauthLogin(request));
    }
}
