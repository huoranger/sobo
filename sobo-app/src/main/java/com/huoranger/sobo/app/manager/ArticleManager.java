package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.IsLogin;
import com.huoranger.sobo.app.support.LoginUserContext;
import com.huoranger.sobo.app.support.PageUtil;
import com.huoranger.sobo.app.support.Pair;
import com.huoranger.sobo.app.transfer.ArticleTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.article.*;
import com.huoranger.sobo.api.response.article.ArticleInfoResponse;
import com.huoranger.sobo.api.response.article.ArticleQueryTypesResponse;
import com.huoranger.sobo.api.response.article.ArticleUserPageResponse;
import com.huoranger.sobo.common.enums.*;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.common.support.EventBus;
import com.huoranger.sobo.domain.entity.*;
import com.huoranger.sobo.domain.entity.value.PostsPageQueryValue;
import com.huoranger.sobo.domain.repository.ArticleRepository;
import com.huoranger.sobo.domain.repository.ArticleTypeRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
@Slf4j
@Component
public class ArticleManager extends AbstractPostsManager {

    @Resource
    private ArticleTypeRepository articleTypeRepository;

    @Resource
    private ArticleRepository articleRepository;

    private static final Long INIT_SORT = 1000L;

    @IsLogin
    @Transactional
    public Long saveArticle(ArticleSaveArticleRequest request) {
        // 创建
        if (ObjectUtils.isEmpty(request.getId())) {
            // 校验类型
            ArticleType articleType = checkArticleType(request.getTypeId());

            // 校验标签
            Set<Tag> selectTags = checkTags(request.getTagIds());

            Article article = ArticleTransfer.toArticle(request, articleType, selectTags, false, INIT_SORT);
            articleRepository.save(article);

            // 触发文章创建事件
            EventBus.emit(EventBus.Topic.ARTICLE_CREATE, article);

            return article.getId();
        }

        // 更新
        // 校验文章
        Article article = articleRepository.get(request.getId());
        CheckUtil.isEmpty(article, ErrorCodeEn.ARTICLE_NOT_EXIST);
        CheckUtil.isFalse(LoginUserContext.getUser().getId().equals(article.getAuthor().getId()), ErrorCodeEn.ARTICLE_NOT_EXIST);

        // 校验类型
        ArticleType articleType = checkArticleType(request.getTypeId());

        // 校验标签
        Set<Tag> selectTags = checkTags(request.getTagIds());

        // 删除旧标签关联关系
        tagRepository.deletePostsMapping(request.getId());

        Article oldArticle = article.copy();
        Article newArticle = ArticleTransfer.toArticle(request, articleType, selectTags, true, INIT_SORT);

        articleRepository.update(newArticle);

        // 触发文章更新事件
        EventBus.emit(EventBus.Topic.ARTICLE_UPDATE, Pair.build(oldArticle, newArticle));

        return request.getId();
    }

    public List<ArticleQueryTypesResponse> queryAllTypes() {
        List<ArticleType> articleTypes = articleTypeRepository.queryByState(AuditStateEn.PASS);

        return ArticleTransfer.toArticleQueryTypesResponses(articleTypes);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public List<ArticleQueryTypesResponse> queryAdminTypes() {
        List<ArticleType> articleTypes = articleTypeRepository.queryByState(null);

        return ArticleTransfer.toArticleQueryTypesResponses(articleTypes);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public PageResponseModel<ArticleQueryTypesResponse> typePage(PageRequestModel<ArticleAdminTypePageRequest> pageRequestModel) {
        ArticleAdminTypePageRequest typePageRequest = pageRequestModel.getFilter();
        ArticleType articleType = ArticleType.builder()
                .name(typePageRequest.getName())
                .description(typePageRequest.getDescription())
                .build();
        if (!ObjectUtils.isEmpty(typePageRequest.getAuditState())) {
            articleType.setAuditState(AuditStateEn.getEntity(typePageRequest.getAuditState()));
        }
        if (!ObjectUtils.isEmpty(typePageRequest.getScope())) {
            articleType.setScope(ArticleTypeScopeEn.getEntity(typePageRequest.getScope()));
        }
        PageResult<ArticleType> pageResult = articleTypeRepository.page(PageUtil.buildPageRequest(pageRequestModel, articleType));

        return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), ArticleTransfer.toArticleQueryTypesResponses(pageResult.getList()));
    }

    @IsLogin
    public List<ArticleQueryTypesResponse> queryEditArticleTypes() {
        List<ArticleTypeScopeEn> scopes = new ArrayList<>();
        scopes.add(ArticleTypeScopeEn.USER);

        User loginUser = LoginUserContext.getUser();
        if (!UserRoleEn.USER.equals(loginUser.getRole() )) {
            scopes.add(ArticleTypeScopeEn.OFFICIAL);
        }

        List<ArticleType> articleTypes = articleTypeRepository.queryByScopesAndState(scopes, AuditStateEn.PASS);

        return ArticleTransfer.toArticleQueryTypesResponses(articleTypes);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void addType(ArticleAddTypeRequest request) {
        CheckUtil.isNotEmpty(articleTypeRepository.query(ArticleType.builder()
                .name(request.getName())
                .build()), ErrorCodeEn.ARTICLE_TYPE_IS_EXIST);

        articleTypeRepository.save(ArticleTransfer.toArticleType(request));
    }

    public PageResponseModel<ArticleUserPageResponse> userPage(PageRequestModel<ArticleUserPageRequest> pageRequestModel) {
        PostsPageQueryValue pageQueryValue = PostsPageQueryValue.builder()
                .auditStates(Arrays.asList(AuditStateEn.PASS.getValue()))
                .build();

        ArticleUserPageRequest pageRequest = pageRequestModel.getFilter();
        if (!ObjectUtils.isEmpty(pageRequest.getTypeName())) {
            ArticleType articleType = articleTypeRepository.getByNameAndState(pageRequest.getTypeName(), AuditStateEn.PASS);
            CheckUtil.isEmpty(articleType, ErrorCodeEn.ARTICLE_TYPE_IS_EXIST);

            pageQueryValue.setTypeId(articleType.getId());
        }

        return pageQuery(pageRequestModel, pageQueryValue);
    }

    public PageResponseModel<ArticleUserPageResponse> authorPage(PageRequestModel<ArticleAuthorPageRequest> pageRequestModel) {
        ArticleAuthorPageRequest request = pageRequestModel.getFilter();

        List<String> auditStates = new ArrayList<>();
        // 设置通过审核
        auditStates.add(AuditStateEn.PASS.getValue());
        // 已经登陆并且是当前用户，添加待审核和不通过的文章
        User user = LoginUserContext.getUser();
        if (!ObjectUtils.isEmpty(user) && user.getId().equals(request.getUserId())) {
            auditStates.add(AuditStateEn.WAIT.getValue());
            auditStates.add(AuditStateEn.REJECT.getValue());
        }

        PostsPageQueryValue pageQueryValue = PostsPageQueryValue.builder()
                .auditStates(auditStates)
                .authorId(request.getUserId())
                .build();

        return pageQuery(pageRequestModel, pageQueryValue);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public PageResponseModel<ArticleUserPageResponse> adminPage(PageRequestModel<ArticleAdminPageRequest> pageRequestModel) {
        ArticleAdminPageRequest request = pageRequestModel.getFilter();

        PostsPageQueryValue pageQueryValue = PostsPageQueryValue.builder()
                .category(PostsCategoryEn.ARTICLE.getValue())
                .authorId(request.getUserId())
                .typeId(request.getTypeId())
                .title(request.getTitle())
                .official(request.getOfficial())
                .marrow(request.getMarrow())
                .top(request.getTop())
                .build();
        if (!ObjectUtils.isEmpty(request.getAuditState()) && !ObjectUtils.isEmpty(AuditStateEn.getEntity(request.getAuditState()))) {
            List<String> auditStates = new ArrayList<>();
            auditStates.add(AuditStateEn.getEntity(request.getAuditState()).getValue());
            pageQueryValue.setAuditStates(auditStates);
        }

        return pageQuery(pageRequestModel, pageQueryValue);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void adminTop(AdminBooleanRequest booleanRequest) {
        BasePosts basePosts = postsRepository.get(booleanRequest.getId());
        CheckUtil.isEmpty(basePosts, ErrorCodeEn.ARTICLE_NOT_EXIST);

        basePosts.setSort(booleanRequest.getIs() ? 1L : INIT_SORT);
        basePosts.setTop(booleanRequest.getIs());
        postsRepository.update(basePosts);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void adminOfficial(AdminBooleanRequest booleanRequest) {
        BasePosts basePosts = postsRepository.get(booleanRequest.getId());
        CheckUtil.isEmpty(basePosts, ErrorCodeEn.ARTICLE_NOT_EXIST);

        basePosts.setOfficial(booleanRequest.getIs());
        postsRepository.update(basePosts);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void adminMarrow(AdminBooleanRequest booleanRequest) {
        BasePosts basePosts = postsRepository.get(booleanRequest.getId());
        CheckUtil.isEmpty(basePosts, ErrorCodeEn.ARTICLE_NOT_EXIST);

        basePosts.setMarrow(booleanRequest.getIs());
        postsRepository.update(basePosts);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void typeAuditState(AdminBooleanRequest booleanRequest) {
        ArticleType articleType = articleTypeRepository.get(booleanRequest.getId());
        CheckUtil.isEmpty(articleType, ErrorCodeEn.ARTICLE_TYPE_IS_EXIST);

        articleType.setAuditState(booleanRequest.getIs() ? AuditStateEn.PASS : AuditStateEn.REJECT);
        articleTypeRepository.update(articleType);
    }

    public ArticleInfoResponse info(Long id) {
        Article article = articleRepository.get(id);
        CheckUtil.isEmpty(article, ErrorCodeEn.ARTICLE_NOT_EXIST);

        if (!AuditStateEn.PASS.equals(article.getAuditState())) {
            User user = LoginUserContext.getUser();
            CheckUtil.isEmpty(user, ErrorCodeEn.ARTICLE_IN_AUDIT_PROCESS);
            CheckUtil.isFalse(user.getId().equals(article.getAuthor().getId()), ErrorCodeEn.ARTICLE_IN_AUDIT_PROCESS);
        }

        // 触发文章查看详情事件
        EventBus.emit(EventBus.Topic.POSTS_INFO, article);

        return ArticleTransfer.toArticleInfoResponse(article);
    }

    private PageResponseModel<ArticleUserPageResponse> pageQuery(PageRequestModel pageRequestModel, PostsPageQueryValue pageQueryValue) {
        pageQueryValue.setCategory(PostsCategoryEn.ARTICLE.getValue());

        PageResult<Article> pageResult = articleRepository.page(pageRequestModel.getPageNo(), pageRequestModel.getPageSize(), pageQueryValue);

        return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), ArticleTransfer.toArticleUserPageResponses(pageResult.getList()));
    }

    private ArticleType checkArticleType(Long typeId) {
        ArticleType articleType = articleTypeRepository.get(typeId);
        CheckUtil.isEmpty(articleType, ErrorCodeEn.ARTICLE_TYPE_IS_EXIST);
        CheckUtil.isFalse(AuditStateEn.PASS.equals(articleType.getAuditState()), ErrorCodeEn.ARTICLE_TYPE_IS_EXIST);

        return articleType;
    }

}
