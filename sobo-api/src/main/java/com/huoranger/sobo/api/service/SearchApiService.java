package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.vo.PostsVO;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
public interface SearchApiService {

    ResultModel<PageResponseModel<PostsVO>> pagePostsSearch(PageRequestModel<String> pageRequestModel);

}
