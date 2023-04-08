package com.huoranger.sobo.domain.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author huoranger
 * @create 2021/5/15
 * @desc
 **/
public interface GithubService {

    JSONObject getUserInfo(String code);

}
