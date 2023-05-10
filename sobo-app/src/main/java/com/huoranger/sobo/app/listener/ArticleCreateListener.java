package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.Article;
import com.huoranger.sobo.domain.entity.Tag;
import com.huoranger.sobo.domain.repository.ArticleTypeRepository;
import com.huoranger.sobo.domain.repository.TagRepository;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author huoranger
 **/
@Component
public class ArticleCreateListener extends EventBus.EventHandler<Article> {

    @Resource
    private TagRepository tagRepository;

    @Resource
    private ArticleTypeRepository articleTypeRepository;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.ARTICLE_CREATE;
    }

    @Override
    public void onMessage(Article article) {
        Set<Long> tagIds = article.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        tagRepository.increaseRefCount(tagIds);

        articleTypeRepository.increaseRefCount(article.getType().getId());
    }
}
