package com.huoranger.sobo.facade.support;

import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.common.enums.ErrorCodeEn;

/**
 * @author huoranger
 * @create 20/7/30
 * @desc
 **/
public class ResultModelUtil {

    public static ResultModel success() {
        return new ResultModel();
    }

    public static <T> ResultModel<T> success(T data) {
        ResultModel<T> resultModel = new ResultModel<T>();
        resultModel.setData(data);

        return resultModel;
    }

    public static ResultModel fail(ErrorCodeEn errorCode) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(errorCode.getCode());
        resultModel.setMessage(errorCode.getMessage());
        resultModel.setSuccess(Boolean.FALSE);

        return resultModel;
    }

    public static ResultModel fail(Integer code, String message) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(code);
        resultModel.setMessage(message);
        resultModel.setSuccess(Boolean.FALSE);

        return resultModel;
    }

}
