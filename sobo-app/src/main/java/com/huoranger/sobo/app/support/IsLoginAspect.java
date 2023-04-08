package com.huoranger.sobo.app.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.enums.ErrorCodeEn;
import com.huoranger.sobo.common.enums.UserRoleEn;
import com.huoranger.sobo.common.exception.BizException;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.domain.entity.User;

/**
 * @author huoranger
 * @create 2020/10/19
 * @desc
 **/
@Component
@Aspect
public class IsLoginAspect {

    /**
     * 获取缓存的登录用户信息，
     * 判断当前登录用户角色权限，并将登录用户设置到请求线程上下文中供业务处理时直接获取
     * @param joinPoint
     * @param isLogin
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.huoranger.sobo..*.*(..)) && @annotation(isLogin)")
    public Object process(ProceedingJoinPoint joinPoint, IsLogin isLogin) throws Throwable {
        User loginUser = LoginUserContext.getUser();
        CheckUtil.isEmpty(loginUser, ErrorCodeEn.USER_NOT_LOGIN);

        UserRoleEn userRoleEn = loginUser.getRole();

        // 超级管理员
        if (UserRoleEn.SUPER_ADMIN.equals(isLogin.role())) {
            if (UserRoleEn.SUPER_ADMIN.equals(userRoleEn)) {
                return joinPoint.proceed();
            }
            throw new BizException(ErrorCodeEn.COMMON_TOKEN_NO_PERMISSION);
        }

        // 管理员
        else if (UserRoleEn.ADMIN.equals(isLogin.role())) {
            if (UserRoleEn.SUPER_ADMIN.equals(userRoleEn)
                    || UserRoleEn.ADMIN.equals(userRoleEn)) {
                return joinPoint.proceed();
            }
            throw new BizException(ErrorCodeEn.COMMON_TOKEN_NO_PERMISSION);
        }

        // 用户
        return joinPoint.proceed();
    }

}
