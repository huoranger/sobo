package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.SearchDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
public interface SearchDAO {

    void insert(SearchDO searchDO);

    void delete(@Param("type") String type, @Param("entityId") Long entityId);

    List<SearchDO> query(SearchDO searchDO);
}
