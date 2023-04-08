package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.IsLogin;
import org.springframework.stereotype.Component;
import com.huoranger.sobo.api.request.file.FileUploadImgRequest;
import com.huoranger.sobo.domain.service.FileService;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/11/23
 * @desc
 **/
@Component
public class FileManager {

    @Resource
    private FileService fileService;

    @IsLogin
    public String uploadImg(FileUploadImgRequest request) {
        return fileService.uploadImg(request.getBase64(), request.getFileName());
    }
}
