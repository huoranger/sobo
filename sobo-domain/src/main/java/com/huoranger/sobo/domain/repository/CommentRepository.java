package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Comment;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/11/5
 * @desc
 **/
public interface CommentRepository {

    void save(Comment comment);

    Comment get(Long id);

    List<Comment> queryByPostsId(Long postsId);

    List<Comment> queryInReplyIds(Set<Long> replyIds);

    PageResult<Comment> page(Integer pageNo, Integer pageSize, Long postsId);

    void deleteByPostsId(Long postsId);

    List<Comment> queryInIds(Set<Long> ids);
}
