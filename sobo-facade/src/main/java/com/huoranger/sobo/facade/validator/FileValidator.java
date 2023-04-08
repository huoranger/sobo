package com.huoranger.sobo.facade.validator;

import com.huoranger.sobo.api.request.file.FileUploadImgRequest;
import com.huoranger.sobo.common.support.CheckUtil;

/**
 * @author huoranger
 * @create 2020/11/23
 * @desc
 **/
public class FileValidator {

    public static void uploadImg(FileUploadImgRequest request) {
        CheckUtil.checkParamToast(request, "request");
        CheckUtil.checkParamToast(request.getBase64(), "base64");
        CheckUtil.checkParamToast(request.getFileName(), "fileName");
    }
}
