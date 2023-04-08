package com.huoranger.sobo.facade.validator;

import com.huoranger.sobo.api.request.faq.FaqSaveFaqRequest;
import com.huoranger.sobo.api.request.faq.FaqSolutionRequest;
import com.huoranger.sobo.common.support.CheckUtil;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc
 **/
public class FaqValidator {

    public static void saveFaq(FaqSaveFaqRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getTitle(), "title");
        CheckUtil.checkParamToast(request.getContentType(), "contentType");
        CheckUtil.checkParamToast(request.getMarkdownContent(), "markdownContent");
        CheckUtil.checkParamToast(request.getHtmlContent(), "htmlContent");
        CheckUtil.checkParamToast(request.getTagIds(), "tagIds");
    }

    public static void solution(FaqSolutionRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getFaqId(), "faqId");
        CheckUtil.checkParamToast(request.getCommentId(), "commentId");
    }
}
