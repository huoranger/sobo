package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.TagDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
public interface TagDAO {

    void insert(TagDO tagDO);

    List<TagDO> query(TagDO tagDO);

    void update(TagDO tagDO);

    List<TagDO> queryInIds(@Param("ids") Set<Long> ids);

    void increaseRefCount(@Param("ids") Set<Long> ids);

    void decreaseRefCount(@Param("ids") Set<Long> ids);

    TagDO get(Long id);
}
