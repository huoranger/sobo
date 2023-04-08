package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.enums.FollowedTypeEn;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/3
 * @desc
 **/
public interface UserFollowRepository {

    List<Long> getAllFollowerIds(Long follower, FollowedTypeEn type);
}
