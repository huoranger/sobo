package com.huoranger.sobo.portal.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import com.huoranger.sobo.common.support.RequestContext;

/**
 * @author huoranger
 * @create 2020/11/25
 * @desc
 **/
@Component
@Aspect
public class ResponseBodyAspect {

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
