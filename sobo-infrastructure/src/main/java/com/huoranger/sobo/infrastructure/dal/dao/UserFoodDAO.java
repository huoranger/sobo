package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.UserFoodDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/3
 * @desc
 **/
public interface UserFoodDAO {

    void insert(UserFoodDO userFoodDO);

    List<UserFoodDO> query(@Param("userId") Long userId);

    void deleteByPostsId(@Param("postsId") Long postsId);
}
