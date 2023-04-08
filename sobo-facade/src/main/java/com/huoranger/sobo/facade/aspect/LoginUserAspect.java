package com.huoranger.sobo.facade.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.app.support.LoginUserContext;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.common.enums.CacheBizTypeEn;
import com.huoranger.sobo.domain.entity.User;
import com.huoranger.sobo.domain.service.CacheService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
@Slf4j
@Component
@Aspect
public class LoginUserAspect {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private CacheService cacheService;

    @Around("execution(* com.huoranger.sobo.api..*.*(..))")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        String token = httpServletRequest.getHeader(Constant.REQUEST_HEADER_TOKEN_KEY);

        if (ObjectUtils.isEmpty(token)) {
            Object tokenObj = httpServletRequest.getAttribute(Constant.REQUEST_HEADER_TOKEN_KEY);
            if (ObjectUtils.isEmpty(tokenObj)) {
                return pjp.proceed();
            }

            token = String.valueOf(tokenObj);
        }

        String cacheString = cacheService.get(CacheBizTypeEn.USER_LOGIN_TOKEN, token);
        if (ObjectUtils.isEmpty(cacheString)) {
            return pjp.proceed();
        }

        LoginUserContext.setToken(token);
        LoginUserContext.setUser(JSON.parseObject(cacheString, User.class));

        try {
            return pjp.proceed();
        } finally {
            LoginUserContext.removeAll();
        }
    }


}
