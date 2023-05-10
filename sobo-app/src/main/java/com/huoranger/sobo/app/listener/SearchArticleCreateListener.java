package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.enums.SearchTypeEn;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.Article;
import com.huoranger.sobo.domain.entity.Search;
import com.huoranger.sobo.domain.service.SearchService;

import javax.annotation.Resource;

/**
 * @author huoranger
 **/
@Component
public class SearchArticleCreateListener extends EventBus.EventHandler<Article> {

    @Resource
    private SearchService searchService;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.ARTICLE_CREATE;
    }

    @Override
    public void onMessage(Article article) {
        searchService.deleteByPostsId(article.getId());

        searchService.save(Search.builder()
                .content(article.getMarkdownContent())
                .entityId(article.getId())
                .title(article.getTitle())
                .type(SearchTypeEn.POSTS)
                .build());
    }
}
