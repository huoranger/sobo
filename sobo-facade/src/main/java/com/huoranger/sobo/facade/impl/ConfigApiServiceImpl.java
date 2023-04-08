package com.huoranger.sobo.facade.impl;

import org.springframework.stereotype.Service;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.model.ResultModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.config.ConfigAddRequest;
import com.huoranger.sobo.api.request.config.ConfigPageRequest;
import com.huoranger.sobo.api.request.config.ConfigUpdateRequest;
import com.huoranger.sobo.api.response.config.ConfigResponse;
import com.huoranger.sobo.api.service.ConfigApiService;
import com.huoranger.sobo.app.manager.ConfigManager;
import com.huoranger.sobo.facade.support.ResultModelUtil;
import com.huoranger.sobo.facade.validator.PageRequestModelValidator;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/12/26
 * @desc
 **/
@Service
public class ConfigApiServiceImpl implements ConfigApiService {

    @Resource
    private ConfigManager configManager;

    @Override
    public ResultModel add(ConfigAddRequest request) {

        configManager.add(request);

        return ResultModelUtil.success();
    }

    @Override
    public ResultModel update(ConfigUpdateRequest request) {

        configManager.update(request);

        return ResultModelUtil.success();
    }

    @Override
    public ResultModel state(AdminBooleanRequest request) {

        configManager.state(request);

        return ResultModelUtil.success();
    }

    @Override
    public ResultModel<PageResponseModel<ConfigResponse>> page(PageRequestModel<ConfigPageRequest> pageRequestModel) {
        PageRequestModelValidator.validator(pageRequestModel);

        return ResultModelUtil.success(configManager.page(pageRequestModel));
    }

    @Override
    public ResultModel<List<ConfigResponse>> queryAvailable(Set<String> types) {

        return ResultModelUtil.success(configManager.queryAvailable(types));
    }
}
