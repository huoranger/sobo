package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.enums.PostsCategoryEn;
import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/7/31
 * @desc
 **/
public interface TagRepository {

    void save(Tag tag);

    List<Tag> query(Tag tag);

    List<Tag> queryByIds(Set<Long> ids);

    List<Tag> queryByState(AuditStateEn auditState);

    void deletePostsMapping(Long articleId);

    void increaseRefCount(Set<Long> ids);

    void decreaseRefCount(Set<Long> ids);

    Tag getByNameAndState(String name, AuditStateEn pass);

    PageResult<Posts> pagePosts(PageRequest<Long> longPageRequest);

    PageResult<Posts> pagePostsByTagIds(PageRequest<Set<Long>> pageRequest);

    PageResult<Tag> page(PageRequest<Tag> tagPageRequest);

    Tag get(Long id);

    void update(Tag tag);
}
