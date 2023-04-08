package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.user.*;
import com.huoranger.sobo.api.response.user.UserInfoResponse;
import com.huoranger.sobo.api.response.user.UserOptLogPageResponse;
import com.huoranger.sobo.api.response.user.UserPageResponse;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
public interface UserApiService {

    ResultModel enable(Long uid);

    ResultModel disable(Long uid);

    ResultModel follow(Long followed);

    ResultModel cancelFollow(Long followed);

    ResultModel<PageResponseModel<UserPageResponse>> pageFollower(PageRequestModel<Long> pageRequestModel);

    ResultModel<PageResponseModel<UserPageResponse>> pageFans(PageRequestModel<Long> pageRequestModel);

    ResultModel<Boolean> hasFollow(Long followed);

    ResultModel<UserInfoResponse> info(String token);

    ResultModel<UserInfoResponse> info(Long uid);

    ResultModel<String> register(UserRegisterRequest request);

    ResultModel<String> emailLogin(UserEmailLoginRequest request);

    ResultModel logout(UserTokenLogoutRequest request);

    ResultModel updateInfo(UserUpdateInfoRequest request);

    ResultModel updateHeadImg(String linkFilenameData);

    ResultModel updatePwd(UserUpdatePwdRequest request);

    ResultModel<PageResponseModel<UserPageResponse>> adminPage(PageRequestModel<UserAdminPageRequest> pageRequestModel);

    ResultModel<PageResponseModel<UserPageResponse>> pageActive(PageRequestModel pageRequestModel);

    ResultModel<PageResponseModel<UserOptLogPageResponse>> pageOptLog(PageRequestModel<UserOptLogPageRequest> pageRequestModel);

    ResultModel updateRole(AdminBooleanRequest booleanRequest);
}
