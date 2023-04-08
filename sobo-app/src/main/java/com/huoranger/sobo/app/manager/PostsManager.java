package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.IsLogin;
import com.huoranger.sobo.app.support.LoginUserContext;
import com.huoranger.sobo.app.support.PageUtil;
import org.springframework.stereotype.Component;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.repository.UserFoodRepository;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/11/25
 * @desc
 **/
@Component
public class PostsManager extends AbstractPostsManager {

    @Resource
    private UserFoodRepository userFoodRepository;

    @IsLogin
    public PageResponseModel<PostsVO> pagePostsFood(PageRequestModel pageRequestModel) {
        PageResult<Posts> pageResult = userFoodRepository.pagePosts(PageUtil.buildPageRequest(pageRequestModel, LoginUserContext.getUser().getId()));

        return pagePostsVO(pageResult);
    }
}
