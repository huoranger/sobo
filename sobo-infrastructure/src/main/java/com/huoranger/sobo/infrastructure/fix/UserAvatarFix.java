//package com.huoranger.sobo.infrastructure.fix;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ObjectUtils;
//import com.huoranger.sobo.common.support.SafesUtil;
//import dao.dal.infrastructure.sobo.developers.com.UserDAO;
//import dataobject.dal.infrastructure.sobo.developers.com.UserDO;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author huoranger
// * @create 2021/5/5
// * @desc
// **/
//@Component
//public class UserAvatarFix {
//
//    @Resource
//    private UserDAO userDAO;
//
//    @Scheduled(cron = "0/30 * * * * ? ")
//    public void task() {
//        List<UserDO> userDOS = userDAO.query(UserDO.builder().build());
//        SafesUtil.ofList(userDOS).forEach(userDO -> {
//            if (!ObjectUtils.isEmpty(userDO.getAvatar())) {
//                String avatar = userDO.getAvatar();
//                userDO.setAvatar(avatar.replaceAll("www.gravatar.com", "sdn.geekzu.org"));
//
//                userDAO.update(userDO);
//            }
//        });
//    }
//}
