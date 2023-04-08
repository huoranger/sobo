package com.huoranger.sobo.infrastructure;

import com.huoranger.sobo.infrastructure.dal.dao.UserFollowDAO;
import org.springframework.stereotype.Repository;
import com.huoranger.sobo.common.enums.FollowedTypeEn;
import com.huoranger.sobo.domain.repository.UserFollowRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/3
 * @desc
 **/
@Repository
public class UserFollowRepositoryImpl implements UserFollowRepository {

    @Resource
    private UserFollowDAO userFollowDAO;

    @Override
    public List<Long> getAllFollowerIds(Long follower, FollowedTypeEn type) {
        return userFollowDAO.getAllFollowerIds(follower, type.getValue());
    }
}
