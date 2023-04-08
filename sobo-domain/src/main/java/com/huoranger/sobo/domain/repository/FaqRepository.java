package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Faq;
import com.huoranger.sobo.domain.entity.value.PostsPageQueryValue;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc
 **/
public interface FaqRepository {

    void save(Faq faq);

    void update(Faq faq);

    void updateEntity(Faq faq);

    Faq get(Long id);

    PageResult<Faq> page(Integer pageNo, Integer pageSize, PostsPageQueryValue pageQueryValue);

    List<Faq> hots(int size);

}
