package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.TagPostsMappingDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
public interface TagPostsMappingDAO {

    void insert(TagPostsMappingDO tagPostsMappingDO);

    List<TagPostsMappingDO> query(TagPostsMappingDO tagPostsMappingDO);

    void deleteByPostsId(@Param("postsId") Long postsId);

    void batchInsert(List<TagPostsMappingDO> tagPostsMappingDOS);

    List<TagPostsMappingDO> queryInPostsIds(@Param("postsIds") Set<Long> postsIds);

    List<TagPostsMappingDO> queryInTagIds(@Param("tagIds") Set<Long> tagIds);
}
