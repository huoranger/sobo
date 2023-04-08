package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
import com.huoranger.sobo.app.support.Pair;
import com.huoranger.sobo.common.enums.SearchTypeEn;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.Faq;
import com.huoranger.sobo.domain.entity.Search;
import com.huoranger.sobo.domain.service.SearchService;

import javax.annotation.Resource;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Component
public class SearchFaqUpdateListener extends EventBus.EventHandler<Pair<Faq>> {

    @Resource
    private SearchService searchService;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.FAQ_UPDATE;
    }

    @Override
    public void onMessage(Pair<Faq> pair) {
        Faq newFaq = pair.getValue1();

        searchService.deleteByPostsId(newFaq.getId());

        searchService.save(Search.builder()
                .content(newFaq.getMarkdownContent())
                .entityId(newFaq.getId())
                .title(newFaq.getTitle())
                .type(SearchTypeEn.POSTS)
                .build());
    }
}
