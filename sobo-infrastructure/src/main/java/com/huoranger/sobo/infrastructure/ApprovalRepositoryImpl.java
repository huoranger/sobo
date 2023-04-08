package com.huoranger.sobo.infrastructure;

import com.huoranger.sobo.infrastructure.dal.dao.UserFollowDAO;
import com.huoranger.sobo.infrastructure.dal.dataobject.UserFollowDO;
import com.huoranger.sobo.infrastructure.transfer.ApprovalTransfer;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.common.enums.FollowedTypeEn;
import com.huoranger.sobo.domain.entity.Approval;
import com.huoranger.sobo.domain.repository.ApprovalRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/1
 * @desc
 **/
@Repository
public class ApprovalRepositoryImpl implements ApprovalRepository {

    @Resource
    private UserFollowDAO userFollowDAO;

    @Override
    public void save(Approval approval) {
        UserFollowDO userFollowDO = ApprovalTransfer.toUserFollowDO(approval);
        userFollowDO.initBase();

        userFollowDAO.insert(userFollowDO);
    }

    @Override
    public void delete(Long approvalId) {
        userFollowDAO.delete(approvalId);
    }

    @Override
    public Approval get(Long postsId, Long userId) {
        List<UserFollowDO> userFollowDOS = userFollowDAO.query(UserFollowDO.builder()
                .follower(userId)
                .followed(postsId)
                .followedType(FollowedTypeEn.POSTS.getValue())
                .build());
        if (ObjectUtils.isEmpty(userFollowDOS)) {
            return null;
        }

        return ApprovalTransfer.toApproval(userFollowDOS.get(0));
    }
}
