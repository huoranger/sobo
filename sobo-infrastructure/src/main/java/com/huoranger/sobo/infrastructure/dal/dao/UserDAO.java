package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/10/29
 * @desc
 **/
public interface UserDAO {

    void insert(UserDO userDO);

    UserDO get(@Param("id") Long id);

    UserDO getByEmail(@Param("email") String email);

    void update(UserDO userDO);

    List<UserDO> queryInIds(@Param("ids") Set<Long> ids);

    List<UserDO> queryOrderLastLoginTime();

    List<UserDO> query(UserDO userDO);
}
