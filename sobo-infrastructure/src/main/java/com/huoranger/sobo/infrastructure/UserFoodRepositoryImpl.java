package com.huoranger.sobo.infrastructure;

import com.huoranger.sobo.infrastructure.dal.dao.UserFoodDAO;
import com.huoranger.sobo.infrastructure.dal.dataobject.UserFoodDO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.entity.UserFood;
import com.huoranger.sobo.domain.repository.UserFoodRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/12/3
 * @desc
 **/
@Repository
public class UserFoodRepositoryImpl extends AbstractPostsRepository implements UserFoodRepository {

    @Resource
    private UserFoodDAO userFoodDAO;

    @Override
    public void batchSave(List<UserFood> userFoods) {
        if (ObjectUtils.isEmpty(userFoods)) {
            return;
        }

        userFoods.forEach(userFood -> {
            try {
                UserFoodDO userFoodDO = UserFoodDO.builder()
                        .userId(userFood.getUserId())
                        .postsId(userFood.getPostsId())
                        .build();
                userFoodDO.initBase();

                userFoodDAO.insert(userFoodDO);
            } catch (Exception e) {
                // 唯一健冲突忽略
            }
        });
    }

    @Override
    public PageResult<Posts> pagePosts(PageRequest<Long> pageRequest) {
        PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());

        List<UserFoodDO> userFoodDOS = userFoodDAO.query(pageRequest.getFilter());
        PageInfo<UserFoodDO> pageInfo = new PageInfo<>(userFoodDOS);

        if (ObjectUtils.isEmpty(userFoodDOS)) {
            return PageResult.build(pageInfo.getTotal(), pageInfo.getSize(), new ArrayList<>());
        }

        List<Long> postsIds = new ArrayList<>();
        userFoodDOS.forEach(userFoodDO -> postsIds.add(userFoodDO.getPostsId()));

        return basePagePosts(postsIds, pageInfo, null);
    }

    @Override
    public void deleteByPostsId(Long postsId) {
        userFoodDAO.deleteByPostsId(postsId);
    }
}
