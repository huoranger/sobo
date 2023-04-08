package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.app.support.Pair;
import com.huoranger.sobo.common.enums.SearchTypeEn;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.Article;
import com.huoranger.sobo.domain.entity.Search;
import com.huoranger.sobo.domain.service.SearchService;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Component
public class SearchArticleUpdateListener  extends EventBus.EventHandler<Pair<Article>> {

    @Resource
    private SearchService searchService;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.ARTICLE_UPDATE;
    }

    @Override
    public void onMessage(Pair<Article> pair) {
        Article newArticle = pair.getValue1();

        searchService.deleteByPostsId(newArticle.getId());

        searchService.save(Search.builder()
                .content(newArticle.getMarkdownContent())
                .entityId(newArticle.getId())
                .title(newArticle.getTitle())
                .type(SearchTypeEn.POSTS)
                .build());
    }
}
