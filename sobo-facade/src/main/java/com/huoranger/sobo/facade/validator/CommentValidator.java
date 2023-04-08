package com.huoranger.sobo.facade.validator;

import com.huoranger.sobo.api.request.comment.CommentCreateRequest;
import com.huoranger.sobo.common.support.CheckUtil;

/**
 * @author huoranger
 * @create 2020/11/6
 * @desc
 **/
public class CommentValidator {

    public static void create(CommentCreateRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getPostsId(), "postsId");
        CheckUtil.checkParamToast(request.getContent(), "content");
    }
}
