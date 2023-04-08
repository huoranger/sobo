package com.huoranger.sobo.portal.controller.client;

import org.springframework.web.bind.annotation.*;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.faq.FaqSaveFaqRequest;
import com.huoranger.sobo.api.request.faq.FaqSolutionRequest;
import com.huoranger.sobo.api.response.faq.FaqInfoResponse;
import com.huoranger.sobo.api.service.FaqApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc
 **/
@RestController
@RequestMapping("/faq-rest")
public class FaqRestController {

    @Resource
    private FaqApiService faqApiService;

    @PostMapping("/save")
    public ResultModel<Long> save(@RequestBody FaqSaveFaqRequest faqRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return faqApiService.saveFaq(faqRequest);
    }

    @PostMapping("/{id}")
    public ResultModel<FaqInfoResponse> get(@PathVariable("id") Long id, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));
        return faqApiService.info(id);
    }

    @PostMapping("/solution")
    public ResultModel solution(@RequestBody FaqSolutionRequest solutionRequest, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        return faqApiService.solution(solutionRequest);
    }
}
