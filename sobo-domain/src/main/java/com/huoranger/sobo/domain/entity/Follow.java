package com.huoranger.sobo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huoranger.sobo.common.enums.FollowedTypeEn;

/**
 * @author huoranger
 * @create 2020/11/20
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Follow extends BaseEntity {

    private Long followed;

    private FollowedTypeEn followedType;

    private Long follower;

}
