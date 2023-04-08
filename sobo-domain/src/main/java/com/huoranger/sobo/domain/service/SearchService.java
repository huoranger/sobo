package com.huoranger.sobo.domain.service;

import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.entity.Search;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
public interface SearchService {

    void deleteByPostsId(Long postsId);

    void save(Search search);

    PageResult<Posts> pagePosts(PageRequest<String> pageRequest);

}
