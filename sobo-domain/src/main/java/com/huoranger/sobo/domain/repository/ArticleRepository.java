package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Article;
import com.huoranger.sobo.domain.entity.value.PostsPageQueryValue;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
public interface ArticleRepository {

    void save(Article article);

    Article get(Long id);

    void update(Article article);

    PageResult<Article> page(Integer pageNo, Integer pageSize, PostsPageQueryValue pageQueryValue);
}
