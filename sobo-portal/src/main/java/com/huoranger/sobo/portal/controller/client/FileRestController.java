package com.huoranger.sobo.portal.controller.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.file.FileUploadImgRequest;
import com.huoranger.sobo.api.service.FileApiService;
import com.huoranger.sobo.common.constant.Constant;
import com.huoranger.sobo.common.enums.ErrorCodeEn;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.common.support.StringUtil;
import com.huoranger.sobo.portal.support.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/11/16
 * @desc
 **/
@Slf4j
@RestController
@RequestMapping("/file-rest")
public class FileRestController {

    @Resource
    private FileApiService fileApiService;

    // .css;.js;.png;.jpeg;.jpg;.woff2;.html;.ico;.gif;.bmp;.svg;.woff;.map
    private static final Set<String> ALLOW_TYPES = Sets.newHashSet("png", "jpeg", "jpg", "ico", "gif", "bmp", "svg");

    @PostMapping("/upload-wang-editor")
    public JSONObject upload0(@RequestParam(value = "image") MultipartFile file, HttpServletRequest request) {
        String fileName = StringUtil.generateUUID();
        ResultModel<String> resultModel = uploadFile(file, fileName, request);

        JSONArray urlArr = new JSONArray();

        int errno = 0;
        if (!resultModel.getSuccess()) {
            errno = resultModel.getCode();
        } else {
            JSONObject url = new JSONObject();
            url.put("url", resultModel.getData());
            url.put("alt", fileName);
            url.put("href", resultModel.getData());

            urlArr.add(url);
        }

        JSONObject res = new JSONObject();
        res.put("errno", errno);
        res.put("data", urlArr);
        return res;
    }

    @PostMapping("/upload")
    public ResultModel<String> upload(@RequestParam(value = "image") MultipartFile file, HttpServletRequest request) {
        String fileName = StringUtil.generateUUID();
        return uploadFile(file, fileName, request);
    }

    private ResultModel<String> uploadFile(MultipartFile file, String fileName, HttpServletRequest request) {
        request.setAttribute(Constant.REQUEST_HEADER_TOKEN_KEY, WebUtil.cookieGetSid(request));

        String fileType = file.getContentType();
        boolean isAllowType = false;
        for (String allowType : ALLOW_TYPES) {
            if (fileType != null && fileType.contains(allowType)) {
                isAllowType = true;
                break;
            }
        }
        CheckUtil.isFalse(isAllowType, ErrorCodeEn.FILE_UPLOAD_NOT_SUPPORT_IMG_TYPE);

        FileUploadImgRequest uploadImgRequest = null;
        try {
            uploadImgRequest = FileUploadImgRequest.builder()
                    .base64(file.getBytes())
                    .fileName(fileName)
                    .build();
        } catch (Exception e) {
            CheckUtil.isTrue(true, ErrorCodeEn.FILE_UPLOAD_FAIL);
        }

        return fileApiService.uploadImg(uploadImgRequest);
    }

}
