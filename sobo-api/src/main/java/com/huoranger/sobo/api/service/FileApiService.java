package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.file.FileUploadImgRequest;

/**
 * @author huoranger
 * @create 2020/11/23
 * @desc
 **/
public interface FileApiService {

    ResultModel<String> uploadImg(FileUploadImgRequest request);

}
