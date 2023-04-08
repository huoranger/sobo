package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.ResultModel;

/**
 * @author huoranger
 * @create 2020/12/1
 * @desc
 **/
public interface ApprovalApiService {

    ResultModel<Long> create(Long postsId);

    ResultModel<Long> delete(Long postsId);

    ResultModel<Boolean> hasApproval(Long postsId);

}
