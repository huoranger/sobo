package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.github.GithubOauthLoginRequest;

/**
 * @author huoranger
 * @create 2021/5/15
 * @desc
 **/
public interface GithubApiService {

    ResultModel<String> oauthLogin(GithubOauthLoginRequest request);

}
