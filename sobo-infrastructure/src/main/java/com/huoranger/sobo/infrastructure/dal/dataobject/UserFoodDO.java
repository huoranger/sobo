package com.huoranger.sobo.infrastructure.dal.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huoranger
 * @create 2020/12/3
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFoodDO extends BaseDO {

    private Long userId;

    private Long postsId;

}
