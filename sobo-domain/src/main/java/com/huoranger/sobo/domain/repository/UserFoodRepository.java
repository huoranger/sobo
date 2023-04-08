package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.entity.UserFood;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/3
 * @desc
 **/
public interface UserFoodRepository {

    void batchSave(List<UserFood> userFoods);

    PageResult<Posts> pagePosts(PageRequest<Long> pageRequest);

    void deleteByPostsId(Long postsId);
}
