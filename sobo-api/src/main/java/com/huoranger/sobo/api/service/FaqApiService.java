package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.response.faq.FaqHotsResponse;
import com.huoranger.sobo.api.response.faq.FaqInfoResponse;
import com.huoranger.sobo.api.response.faq.FaqUserPageResponse;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.faq.*;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc
 **/
public interface FaqApiService {

    ResultModel<Long> saveFaq(FaqSaveFaqRequest request);

    ResultModel<PageResponseModel<FaqUserPageResponse>> adminPage(PageRequestModel<FaqAdminPageRequest> pageRequestModel);

    ResultModel<PageResponseModel<FaqUserPageResponse>> userPage(PageRequestModel<FaqUserPageRequest> pageRequestModel);

    ResultModel<PageResponseModel<FaqUserPageResponse>> authorPage(PageRequestModel<FaqAuthorPageRequest> pageRequestModel);

    ResultModel<FaqInfoResponse> info(Long id);

    ResultModel<List<FaqHotsResponse>> hots(int size);

    ResultModel solution(FaqSolutionRequest request);
}
