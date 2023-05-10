package com.huoranger.sobo.portal.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import com.huoranger.sobo.common.exception.BizException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 **/
@Component
@Aspect
public class GetMappingAspect {

    @Resource
    private HttpServletRequest request;

    /**
     * 对get请求错误统一处理，返回error视图
     * @param joinPoint
     * @param getMapping
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.huoranger.sobo.portal..*.*(..)) && @annotation(getMapping)")
    public Object process(ProceedingJoinPoint joinPoint, GetMapping getMapping) throws Throwable {
        String toastMessage;

        try {
            return joinPoint.proceed();
        } catch (ViewException viewException) {
            toastMessage = viewException.getMessage();
        } catch (BizException bizException) {
            toastMessage = bizException.getMessage();
        } catch (Exception e) {
            toastMessage = "未知异常";
        }

        request.setAttribute("toast", toastMessage);
        return "error";
    }

}
