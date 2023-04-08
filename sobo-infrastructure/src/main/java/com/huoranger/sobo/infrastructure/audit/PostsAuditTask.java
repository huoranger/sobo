package com.huoranger.sobo.infrastructure.audit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.infrastructure.dal.dao.PostsDAO;
import com.huoranger.sobo.infrastructure.dal.dataobject.PostsDO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/11/1
 * @desc 帖子自动审核通过
 **/
@Service
public class PostsAuditTask {

    @Resource
    private PostsDAO postsDAO;

    @Scheduled(cron = "0/2 * * * * ? ")
    public void task() {
        List<PostsDO> postsDOS = postsDAO.query(PostsDO.builder()
                .auditState(AuditStateEn.WAIT.getValue())
                .build());
        SafesUtil.ofList(postsDOS).forEach(postsDO -> {
            postsDO.setAuditState(AuditStateEn.PASS.getValue());
            postsDAO.update(postsDO);
        });
    }

}
