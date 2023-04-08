package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.faq.*;
import com.huoranger.sobo.api.response.faq.FaqHotsResponse;
import com.huoranger.sobo.api.response.faq.FaqInfoResponse;
import com.huoranger.sobo.api.response.faq.FaqUserPageResponse;
import com.huoranger.sobo.api.service.FaqApiService;
import com.huoranger.sobo.app.manager.FaqManager;
import com.huoranger.sobo.app.support.IsLogin;
import com.huoranger.sobo.common.enums.UserRoleEn;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.FaqValidator;
import com.huoranger.sobo.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc
 **/
@Service
public class FaqApiServiceImpl implements FaqApiService {

    @Resource
    private FaqManager faqManager;

    @Override
    public ResultModel<Long> saveFaq(FaqSaveFaqRequest request) {
        FaqValidator.saveFaq(request);

        return ResultModelUtil.success(faqManager.saveFaq(request));
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    @Override
    public ResultModel<PageResponseModel<FaqUserPageResponse>> adminPage(PageRequestModel<FaqAdminPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(faqManager.adminPage(pageRequestModel));
    }

    @Override
    public ResultModel<PageResponseModel<FaqUserPageResponse>> userPage(PageRequestModel<FaqUserPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(faqManager.userPage(pageRequestModel));
    }

    @Override
    public ResultModel<PageResponseModel<FaqUserPageResponse>> authorPage(PageRequestModel<FaqAuthorPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(faqManager.authorPage(pageRequestModel));
    }

    @Override
    public ResultModel<FaqInfoResponse> info(Long id) {
        return ResultModelUtil.success(faqManager.info(id));
    }

    @Override
    public ResultModel<List<FaqHotsResponse>> hots(int size) {
        return ResultModelUtil.success(faqManager.hots(size));
    }

    @Override
    public ResultModel solution(FaqSolutionRequest request) {
        FaqValidator.solution(request);

        faqManager.solution(request);

        return ResultModelUtil.success();
    }
}
