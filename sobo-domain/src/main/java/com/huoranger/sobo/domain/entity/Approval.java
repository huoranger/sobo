package com.huoranger.sobo.domain.entity;

import lombok.*;

/**
 * @author huoranger
 * @create 2020/7/30
 * @desc 文章点赞/问答关注
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Approval extends BaseEntity {

    private Long postsId;

    private Long userId;

}
