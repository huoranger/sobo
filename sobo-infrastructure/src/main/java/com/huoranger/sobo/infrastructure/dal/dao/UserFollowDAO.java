package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.UserFollowDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huoranger
 * @create 20/11/19
 * @desc
 **/
public interface UserFollowDAO {

    void insert(UserFollowDO userFollowDO);

    List<UserFollowDO> query(UserFollowDO userFollowDO);

    void delete(@Param("id") Long id);

    List<Long> getAllFollowerIds(@Param("follower") Long follower, @Param("type") String type);
}
