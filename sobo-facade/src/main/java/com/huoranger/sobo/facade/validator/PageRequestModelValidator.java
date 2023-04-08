package com.huoranger.sobo.facade.validator;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.common.support.CheckUtil;

/**
 * @author huoranger
 * @create 20/9/9
 * @desc
 **/
public class PageRequestModelValidator {

    public static void validator(PageRequestModel pageRequestModel) {
        CheckUtil.checkParamToast(pageRequestModel, "pageRequestModel");
        CheckUtil.checkParamToast(pageRequestModel.getPageNo(), "pageNo");
        CheckUtil.checkParamToast(pageRequestModel.getPageSize(), "pageSize");
        CheckUtil.checkParamToast(pageRequestModel.getFilter(), "filter");
    }

}
