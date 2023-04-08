package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.CacheDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 20/7/23
 * @desc
 **/
public interface CacheDAO {

    void insertBatch(List<CacheDO> cacheDOS);

    void insert(CacheDO cacheDO);

    List<CacheDO> getAll();

    void batchDeleteByKeys(@Param("keys") Set<String> keys);

    void updateByKey(@Param("key") String key, @Param("value") String value);

}