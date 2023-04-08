package com.huoranger.sobo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huoranger.sobo.common.enums.ArticleTypeScopeEn;
import com.huoranger.sobo.common.enums.AuditStateEn;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleType extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 引用统计
     */
    private Long refCount;

    /**
     * 作用域
     */
    private ArticleTypeScopeEn scope;

    /**
     * 创建人
     */
    private Long creatorId;

    /**
     * 审核状态
     */
    private AuditStateEn auditState;

}
