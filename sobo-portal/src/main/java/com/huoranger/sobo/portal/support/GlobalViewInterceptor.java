package com.huoranger.sobo.portal.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.response.user.UserInfoResponse;
import com.huoranger.sobo.api.service.MessageApiService;
import com.huoranger.sobo.api.service.UserApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.common.support.GlobalViewConfig;
import com.huoranger.sobo.common.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huoranger
 * @create 2020/10/29
 * @desc
 **/
@Slf4j
@Component
public class GlobalViewInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserApiService userApiService;

    @Resource
    private MessageApiService messageApiService;

    @Resource
    private GlobalViewConfig globalViewConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContext.init();

        String sid = WebUtil.cookieGetSid(request);
        if (ObjectUtils.isEmpty(sid)) {
            return true;
        }

        if (!ObjectUtils.isEmpty(sid)) {
            ResultModel<UserInfoResponse> resultModel = userApiService.info(sid);
            if (resultModel.getSuccess() && !ObjectUtils.isEmpty(resultModel.getData())) {
                WebContext.setCurrentSid(sid);
                WebContext.setCurrentUser(resultModel.getData());
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            UserInfoResponse loginUserInfo = WebContext.getCurrentUser();

            // 重定向请求不需要添加
            if (!ObjectUtils.isEmpty(modelAndView) && !modelAndView.getViewName().startsWith(WebConst.REQUEST_REDIRECT_PREFIX)) {
                if (!ObjectUtils.isEmpty(loginUserInfo)) {
                    request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

                    Map<String, Object> loginUser = new HashMap<>();
                    loginUser.put("id", loginUserInfo.getId());
                    loginUser.put("nickname", loginUserInfo.getNickname());
                    loginUser.put("avatar", loginUserInfo.getAvatar());
                    loginUser.put("role", loginUserInfo.getRole());
                    loginUser.put("unReadMsgNumber", countUnRead());

                    modelAndView.getModel().put("loginUser", loginUser);
                }
                modelAndView.getModel().put("isLogin", !ObjectUtils.isEmpty(loginUserInfo));

                modelAndView.getModel().put("globalConfig", globalViewConfig);
            }
        } finally {
            WebContext.removeAll();
            RequestContext.removeAll();
        }
    }

    private Long countUnRead() {
        ResultModel<Long> countResult = messageApiService.countUnRead();
        if (countResult.getSuccess() && !ObjectUtils.isEmpty(countResult.getData())) {
            return countResult.getData();
        }
        return 0L;
    }

}
