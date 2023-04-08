package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.OptLog;

/**
 * @author huoranger
 * @create 2020/10/20
 * @desc
 **/
public interface OptLogRepository {

    void save(OptLog optLog);

    PageResult<OptLog> page(PageRequest<OptLog> pageRequest);
}
