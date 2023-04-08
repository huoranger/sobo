package com.huoranger.sobo.app.listener;

import org.springframework.stereotype.Component;
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
public class SearchFaqCreateListener extends EventBus.EventHandler<Faq> {

    @Resource
    private SearchService searchService;

    @Override
    public EventBus.Topic topic() {
        return EventBus.Topic.FAQ_CREATE;
    }

    @Override
    public void onMessage(Faq faq) {
        searchService.deleteByPostsId(faq.getId());

        searchService.save(Search.builder()
                .content(faq.getMarkdownContent())
                .entityId(faq.getId())
                .title(faq.getTitle())
                .type(SearchTypeEn.POSTS)
                .build());
    }
}
