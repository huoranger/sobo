package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.service.SearchApiService;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.app.manager.SearchManager;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Service
public class SearchApiServiceImpl implements SearchApiService {

    @Resource
    private SearchManager searchManager;

    @Override
    public ResultModel<PageResponseModel<PostsVO>> pagePostsSearch(PageRequestModel<String> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(searchManager.pagePostsSearch(pageRequestModel));
    }

}
