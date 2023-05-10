package com.huoranger.sobo.portal.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import com.huoranger.sobo.common.support.RequestContext;

/**
 * @author huoranger
 **/
@Component
@Aspect
public class ResponseBodyAspect {

    /**
     * 对post请求统一统一初始化请求上下文（任意类的任何带postMapping注解的方法）
     * @param joinPoint
     * @param postMapping
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.huoranger.sobo.portal..*.*(..)) && @annotation(postMapping)")
    public Object process(ProceedingJoinPoint joinPoint, PostMapping postMapping) throws Throwable {
        RequestContext.init();
        try {
            return joinPoint.proceed();
        } finally {
            RequestContext.removeAll();
        }
    }
}
