package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.IsLogin;
import com.huoranger.sobo.app.support.LoginUserContext;
import com.huoranger.sobo.app.transfer.PostsTransfer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.enums.ErrorCodeEn;
import com.huoranger.sobo.common.enums.UserRoleEn;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.entity.Comment;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.entity.Tag;
import com.huoranger.sobo.domain.repository.CommentRepository;
import com.huoranger.sobo.domain.repository.PostsRepository;
import com.huoranger.sobo.domain.repository.TagRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc
 **/
public abstract class AbstractPostsManager {

    @Resource
    TagRepository tagRepository;

    @Resource
    PostsRepository postsRepository;

    @Resource
    CommentRepository commentRepository;

    Set<Tag> checkTags(Set<Long> tagIds) {
        List<Tag> tags = tagRepository.queryByIds(tagIds);
        Set<Tag> selectTags = SafesUtil.ofList(tags).stream()
                .filter(tag -> AuditStateEn.PASS.equals(tag.getAuditState()))
                .collect(Collectors.toSet());
        CheckUtil.isEmpty(selectTags, ErrorCodeEn.TAG_NOT_EMPTY);

        return selectTags;
    }

    PageResponseModel<PostsVO> pagePostsVO(PageResult<Posts> pageResult) {
        Set<Long> solutionIds = SafesUtil.ofList(pageResult.getList()).stream()
                .filter(posts -> !ObjectUtils.isEmpty(posts.getSolutionId()) && posts.getSolutionId() != 0L)
                .map(Posts::getSolutionId).collect(Collectors.toSet());

        if (ObjectUtils.isEmpty(solutionIds)) {
            return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), PostsTransfer.toPostsVOs(pageResult.getList(), new ArrayList<>()));
        }

        List<Comment> comments = commentRepository.queryInIds(solutionIds);
        if (ObjectUtils.isEmpty(comments)) {
            return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), PostsTransfer.toPostsVOs(pageResult.getList(), new ArrayList<>()));
        }

        return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), PostsTransfer.toPostsVOs(pageResult.getList(), comments));
    }

    @IsLogin
    @Transactional
    public void delete(Long id) {
        BasePosts basePosts = postsRepository.get(id);
        CheckUtil.isEmpty(basePosts, ErrorCodeEn.POSTS_NOT_EXIST);
        CheckUtil.isFalse(LoginUserContext.getUser().getId().equals(basePosts.getAuthorId()), ErrorCodeEn.POSTS_NOT_EXIST);

        tagRepository.deletePostsMapping(id);
        commentRepository.deleteByPostsId(id);
        postsRepository.delete(id);
        EventBus.emit(EventBus.Topic.POSTS_DELETE, basePosts);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void auditState(AdminBooleanRequest booleanRequest) {
        BasePosts basePosts = postsRepository.get(booleanRequest.getId());
        CheckUtil.isEmpty(basePosts, ErrorCodeEn.ARTICLE_NOT_EXIST);

        basePosts.setAuditState(booleanRequest.getIs() ? AuditStateEn.PASS : AuditStateEn.REJECT);
        postsRepository.update(basePosts);
    }
}
