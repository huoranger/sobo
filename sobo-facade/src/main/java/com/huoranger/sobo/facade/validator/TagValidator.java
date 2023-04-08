package com.huoranger.sobo.facade.validator;

import com.huoranger.sobo.api.request.tag.TagCreateRequest;
import com.huoranger.sobo.common.support.CheckUtil;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
public class TagValidator {

    public static void create(TagCreateRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getName(), "name");
        CheckUtil.checkParamToast(request.getGroupName(), "groupName");
        CheckUtil.checkParamToast(request.getDescription(), "description");
    }

}
