package com.huoranger.sobo.app.support;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;

import java.util.List;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
public class PageUtil {

    public static <T> PageRequest<T> buildPageRequest(PageRequestModel pageRequestModel, T filter) {
        return PageRequest.build(pageRequestModel.getPageNo(), pageRequestModel.getPageSize(), filter);
    }

    public static PageRequest buildPageRequest(PageRequestModel pageRequestModel) {
        return PageRequest.build(pageRequestModel.getPageNo(), pageRequestModel.getPageSize());
    }

    public static <T> PageResponseModel<T> buildPageResponseModel(PageResult pageResult, List<T> list) {
        PageResponseModel<T> pageResponseModel = new PageResponseModel<>();
        pageResponseModel.setSize(pageResult.getSize());
        pageResponseModel.setTotal(pageResult.getTotal());
        pageResponseModel.setList(list);

        return pageResponseModel;
    }


}
