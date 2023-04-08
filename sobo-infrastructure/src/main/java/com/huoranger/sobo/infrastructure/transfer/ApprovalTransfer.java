package com.huoranger.sobo.infrastructure.transfer;

import com.huoranger.sobo.common.enums.FollowedTypeEn;
import com.huoranger.sobo.domain.entity.Approval;
import com.huoranger.sobo.infrastructure.dal.dataobject.UserFollowDO;

/**
 * @author huoranger
 * @create 2020/12/1
 * @desc
 **/
public class ApprovalTransfer {

    public static UserFollowDO toUserFollowDO(Approval approval) {
        return UserFollowDO.builder()
                .followedType(FollowedTypeEn.POSTS.getValue())
                .followed(approval.getPostsId())
                .follower(approval.getUserId())
                .build();
    }

    public static Approval toApproval(UserFollowDO userFollowDO) {
        Approval approval = Approval.builder()
                .userId(userFollowDO.getFollower())
                .postsId(userFollowDO.getFollowed())
                .build();
        approval.setCreateAt(userFollowDO.getCreateAt());
        approval.setId(userFollowDO.getId());
        approval.setUpdateAt(userFollowDO.getUpdateAt());

        return approval;
    }
}
