package com.huoranger.sobo.api.service;

import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.config.ConfigAddRequest;
import com.huoranger.sobo.api.request.config.ConfigPageRequest;
import com.huoranger.sobo.api.request.config.ConfigUpdateRequest;
import com.huoranger.sobo.api.response.config.ConfigResponse;

import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/12/26
 * @desc
 **/
public interface ConfigApiService {

    /**
     * 添加
     * @param request
     * @return
     */
    ResultModel add(ConfigAddRequest request);

    /**
     * 更新
     * @param request
     * @return
     */
    ResultModel update(ConfigUpdateRequest request);

    /**
     * 更新状态
     * @param request
     * @return
     */
    ResultModel state(AdminBooleanRequest request);

    /**
     * 分页查询
     * @param pageRequestModel
     * @return
     */
    ResultModel<PageResponseModel<ConfigResponse>> page(PageRequestModel<ConfigPageRequest> pageRequestModel);

    /**
     * 类别查询可用
     * @param types
     * @return
     */
    ResultModel<List<ConfigResponse>> queryAvailable(Set<String> types);
}
