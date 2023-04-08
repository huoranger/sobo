package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.service.ApprovalApiService;
import com.huoranger.sobo.app.manager.ApprovalManager;
import com.huoranger.sobo.facade.support.ResultModelUtil;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/1
 * @desc
 **/
@Service
public class ApprovalApiServiceImpl implements ApprovalApiService {

    @Resource
    private ApprovalManager approvalManager;

    @Override
    public ResultModel<Long> create(Long postsId) {
        return ResultModelUtil.success(approvalManager.create(postsId));
    }

    @Override
    public ResultModel<Long> delete(Long postsId) {
        return ResultModelUtil.success(approvalManager.delete(postsId));
    }

    @Override
    public ResultModel<Boolean> hasApproval(Long postsId) {
        return ResultModelUtil.success(approvalManager.hasApproval(postsId));
    }

}
