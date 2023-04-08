package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.file.FileUploadImgRequest;
import com.huoranger.sobo.api.service.FileApiService;
import com.huoranger.sobo.app.manager.FileManager;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.FileValidator;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/11/23
 * @desc
 **/
@Service
public class FileApiServiceImpl implements FileApiService {

    @Resource
    private FileManager fileManager;

    @Override
    public ResultModel<String> uploadImg(FileUploadImgRequest request) {
        FileValidator.uploadImg(request);

        return ResultModelUtil.success(fileManager.uploadImg(request));
    }
}
