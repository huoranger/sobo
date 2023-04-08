package com.huoranger.sobo.facade.validator;

import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.api.request.user.*;
import com.huoranger.sobo.common.support.CheckUtil;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
public class UserValidator {

    public static void register(UserRegisterRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getEmail(), "email");
        CheckUtil.checkParamToast(request.getNickname(), "nickname");
        CheckUtil.checkParamToast(request.getPassword(), "password");
    }

    public static void emailLogin(UserEmailLoginRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getEmail(), "email");
        CheckUtil.checkParamToast(request.getPassword(), "password");
    }

    public static void updateInfo(UserUpdateInfoRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getEmail(), "email");
        CheckUtil.checkParamToast(request.getNickname(), "nickname");
        CheckUtil.checkParamToast(request.getSignature(), "signature");
    }

    public static void updatePwd(UserUpdatePwdRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getOldPassword(), "oldPassword");
        CheckUtil.checkParamToast(request.getNewPassword(), "newPassword");
    }

    public static void updateRole(UserUpdateRoleRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getUserId(), "userId");
        CheckUtil.checkParamToast(request.getRole(), "role");
    }

    public static void adminPage(UserAdminPageRequest request) {

    }

    public static void logout(UserTokenLogoutRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getToken(), "token");
    }
}
