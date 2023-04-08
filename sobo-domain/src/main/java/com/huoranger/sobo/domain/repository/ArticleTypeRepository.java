package com.huoranger.sobo.domain.repository;

import com.huoranger.sobo.common.enums.ArticleTypeScopeEn;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.model.PageRequest;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.domain.entity.ArticleType;

import java.util.List;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
public interface ArticleTypeRepository {

    void save(ArticleType articleType);

    List<ArticleType> query(ArticleType articleType);

    List<ArticleType> queryByState(AuditStateEn auditState);

    List<ArticleType> queryByScopesAndState(List<ArticleTypeScopeEn> scopes, AuditStateEn auditState);

    void update(ArticleType articleType);

    ArticleType get(Long id);

    ArticleType getByNameAndState(String typeName, AuditStateEn pass);

    void increaseRefCount(Long id);

    void decreaseRefCount(Long id);

    PageResult<ArticleType> page(PageRequest<ArticleType> articleTypePageRequest);
}
