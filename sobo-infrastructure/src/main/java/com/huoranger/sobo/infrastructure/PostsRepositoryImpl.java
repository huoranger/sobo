package com.huoranger.sobo.infrastructure;

import com.huoranger.sobo.infrastructure.dal.dao.PostsDAO;
import com.huoranger.sobo.infrastructure.dal.dataobject.PostsDO;
import com.huoranger.sobo.infrastructure.transfer.PostsTransfer;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.domain.entity.BasePosts;
import com.huoranger.sobo.domain.repository.PostsRepository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/11/6
 * @desc
 **/
@Repository
public class PostsRepositoryImpl implements PostsRepository {

    @Resource
    private PostsDAO postsDAO;

    @Override
    public BasePosts get(Long id) {
        return PostsTransfer.toBasePosts(postsDAO.get(id));
    }

    @Override
    public List<BasePosts> queryInIds(Set<Long> ids) {
        return PostsTransfer.toBasePostsList(postsDAO.queryInIds(ids));
    }

    @Override
    public List<Long> getAllIdByAuthorId(Long authorId) {
        return postsDAO.getAllIdByAuthorId(authorId, AuditStateEn.PASS.getValue());
    }

    @Override
    public void increaseComments(Long id, Date updateAt) {
        postsDAO.increaseComments(id, updateAt);
    }

    @Override
    public void decreaseComments(Long id, Date updateAt) {
        postsDAO.decreaseComments(id, updateAt);
    }

    @Override
    public void increaseViews(Long id, Date updateAt) {
        postsDAO.increaseViews(id, updateAt);
    }

    @Override
    public void increaseApproval(Long id, Date updateAt) {
        postsDAO.increaseApproval(id, updateAt);
    }

    @Override
    public void decreaseApproval(Long id, Date updateAt) {
        postsDAO.decreaseApproval(id, updateAt);
    }

    @Override
    public void delete(Long id) {
        postsDAO.delete(id);
    }

    @Override
    public void update(BasePosts basePosts) {
        PostsDO postsDO = PostsDO.builder()
                .sort(basePosts.getSort())
                .top(basePosts.getTop())
                .marrow(basePosts.getMarrow())
                .official(basePosts.getOfficial())
                .build();
        if (!ObjectUtils.isEmpty(basePosts.getAuditState())) {
            postsDO.setAuditState(basePosts.getAuditState().getValue());
        }
        postsDO.setId(basePosts.getId());

        postsDAO.update(postsDO);
    }
}
