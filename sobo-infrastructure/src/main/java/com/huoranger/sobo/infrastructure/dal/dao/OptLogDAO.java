package com.huoranger.sobo.infrastructure.dal.dao;

import com.huoranger.sobo.infrastructure.dal.dataobject.OptLogDO;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/4
 * @desc
 **/
public interface OptLogDAO {

    void insert(OptLogDO optLogDO);

    List<OptLogDO> query(OptLogDO optLogDO);
}
