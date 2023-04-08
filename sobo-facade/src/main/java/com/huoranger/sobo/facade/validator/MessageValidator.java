package com.huoranger.sobo.facade.validator;

import com.huoranger.sobo.api.request.message.MessagePageRequest;
import com.huoranger.sobo.common.enums.MessageTypeEn;
import com.huoranger.sobo.common.support.CheckUtil;

/**
 * @author huoranger
 * @create 2020/12/5
 * @desc
 **/
public class MessageValidator {

    public static void page(MessagePageRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getTypeDesc(), "typeDesc");
        CheckUtil.checkParamToast(MessageTypeEn.getEntityByDesc(request.getTypeDesc()), "不存在的消息类型");
    }
}
