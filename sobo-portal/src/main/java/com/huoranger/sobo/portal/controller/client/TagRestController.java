package com.huoranger.sobo.portal.controller.client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.response.tag.TagQueryResponse;
import com.huoranger.sobo.api.service.TagApiService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/11/13
 * @desc
 **/
@RestController
@RequestMapping("/tag-rest")
public class TagRestController {

    @Resource
    private TagApiService tagApiService;

    @PostMapping("/all")
    public ResultModel<List<TagQueryResponse>> all() {
        return tagApiService.queryAll();
    }
}
