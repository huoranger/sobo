package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.IsLogin;
import com.huoranger.sobo.app.support.LoginUserContext;
import com.huoranger.sobo.app.support.PageUtil;
import com.huoranger.sobo.app.transfer.ConfigTransfer;
import org.springframework.stereotype.Component;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.config.ConfigAddRequest;
import com.huoranger.sobo.api.request.config.ConfigPageRequest;
import com.huoranger.sobo.api.request.config.ConfigUpdateRequest;
import com.huoranger.sobo.api.response.config.ConfigResponse;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.enums.ErrorCodeEn;
import com.huoranger.sobo.common.enums.UserRoleEn;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.domain.entity.Config;
import com.huoranger.sobo.domain.repository.ConfigRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/12/26
 * @desc
 **/
@Component
public class ConfigManager {

    @Resource
    private ConfigRepository configRepository;

    @IsLogin(role = UserRoleEn.ADMIN)
    public void add(ConfigAddRequest request) {
        Config config = ConfigTransfer.toConfig(request);
        config.setCreator(LoginUserContext.getUser().getId());

        configRepository.save(config);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void update(ConfigUpdateRequest request) {
        Config config = configRepository.get(request.getId());
        CheckUtil.isEmpty(config, ErrorCodeEn.CONFIG_NOT_EXIST);

        ConfigTransfer.update(config, request);
        configRepository.update(config);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void state(AdminBooleanRequest request) {
        Config config = configRepository.get(request.getId());
        CheckUtil.isEmpty(config, ErrorCodeEn.CONFIG_NOT_EXIST);

        config.setState(request.getIs() ? AuditStateEn.PASS : AuditStateEn.REJECT);
        configRepository.update(config);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public PageResponseModel<ConfigResponse> page(PageRequestModel<ConfigPageRequest> pageRequestModel) {
        ConfigPageRequest request = pageRequestModel.getFilter();

        PageResult<Config> pageResult = configRepository.page(PageUtil.buildPageRequest(pageRequestModel, ConfigTransfer.toConfig(request)));

        return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), ConfigTransfer.toConfigResponses(pageResult.getList()));
    }

    public List<ConfigResponse> queryAvailable(Set<String> types) {
        return ConfigTransfer.toConfigResponses(configRepository.queryAvailable(types));
    }
}
