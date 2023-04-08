package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.PageUtil;
import org.springframework.stereotype.Component;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.service.SearchService;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Component
public class SearchManager extends AbstractPostsManager {

    @Resource
    private SearchService searchService;

    public PageResponseModel<PostsVO> pagePostsSearch(PageRequestModel<String> pageRequestModel) {
        PageResult<Posts> pageResult = searchService.pagePosts(PageUtil.buildPageRequest(pageRequestModel, pageRequestModel.getFilter()));

        return pagePostsVO(pageResult);
    }
}
