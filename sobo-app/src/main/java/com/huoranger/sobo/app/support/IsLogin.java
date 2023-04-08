package com.huoranger.sobo.app.support;

import com.huoranger.sobo.common.enums.UserRoleEn;

import java.lang.annotation.*;

/**
 * @author huoranger
 * @create 2020/10/19
 * @desc
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IsLogin {

    /**
     * 登录角色
     * @return
     */
    UserRoleEn role() default UserRoleEn.USER;

}