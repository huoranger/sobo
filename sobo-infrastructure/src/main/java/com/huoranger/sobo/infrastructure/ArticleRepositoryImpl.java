package com.huoranger.sobo.infrastructure;

import com.huoranger.sobo.infrastructure.transfer.ArticleTypeTransfer;
import com.huoranger.sobo.infrastructure.transfer.PostsTransfer;
import com.huoranger.sobo.infrastructure.transfer.TagTransfer;
import com.huoranger.sobo.infrastructure.transfer.UserTransfer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.Article;
import com.huoranger.sobo.domain.entity.ArticleType;
import com.huoranger.sobo.domain.entity.Tag;
import com.huoranger.sobo.domain.entity.User;
import com.huoranger.sobo.domain.entity.value.PostsPageQueryValue;
import com.huoranger.sobo.domain.repository.ArticleRepository;
import com.huoranger.sobo.infrastructure.dal.dao.*;
import com.huoranger.sobo.infrastructure.dal.dataobject.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    @Resource
    private PostsDAO postsDAO;

    @Resource
    private ArticleTypeDAO articleTypeDAO;

    @Resource
    private TagPostsMappingDAO tagPostsMappingDAO;

    @Resource
    private TagDAO tagDAO;

    @Resource
    private UserDAO userDAO;

    @Override
    public void save(Article article) {
        PostsDO postsDO = PostsTransfer.toPostsDO(article);
        postsDO.setCreateAt(new Date());

        postsDAO.insert(postsDO);

        tagPostsMappingDAO.batchInsert(SafesUtil.ofSet(article.getTags()).stream().map(tag -> {
            TagPostsMappingDO tagPostsMappingDO = TagPostsMappingDO.builder()
                    .tagId(tag.getId())
                    .postsId(postsDO.getId())
                    .build();
            tagPostsMappingDO.initBase();
            return tagPostsMappingDO;
        }).collect(Collectors.toList()));

        article.setId(postsDO.getId());
    }

    @Override
    public Article get(Long id) {
        PostsDO postsDO = postsDAO.get(id);
        if (ObjectUtils.isEmpty(postsDO)) {
            return null;
        }

        UserDO userDO = userDAO.get(postsDO.getAuthorId());
        if (ObjectUtils.isEmpty(userDO)) {
            return null;
        }
        User user = UserTransfer.toUser(userDO);

        ArticleType articleType = ArticleTypeTransfer.toArticleType(articleTypeDAO.get(postsDO.getTypeId()));

        List<TagPostsMappingDO> tagPostsMappingDOS = tagPostsMappingDAO.query(TagPostsMappingDO.builder()
                .postsId(id)
                .build());
        Set<Long> tagIds = SafesUtil.ofList(tagPostsMappingDOS).stream()
                .map(TagPostsMappingDO::getTagId)
                .collect(Collectors.toSet());
        if (ObjectUtils.isEmpty(tagIds)) {
            return PostsTransfer.toArticle(postsDO, user, articleType, new ArrayList<>());
        }

        List<Tag> tags = TagTransfer.toTags(tagDAO.queryInIds(tagIds));

        return PostsTransfer.toArticle(postsDO, user, articleType, tags);
    }

    @Override
    public void update(Article article) {
        postsDAO.update(PostsTransfer.toPostsDO(article));

        List<TagPostsMappingDO> tagPostsMappingDOS = SafesUtil.ofSet(article.getTags()).stream().map(tag -> {
            TagPostsMappingDO tagPostsMappingDO = TagPostsMappingDO.builder()
                    .postsId(article.getId())
                    .tagId(tag.getId())
                    .build();
            tagPostsMappingDO.initBase();
            return tagPostsMappingDO;
        }).collect(Collectors.toList());
        tagPostsMappingDAO.batchInsert(tagPostsMappingDOS);
    }

    @Override
    public PageResult<Article> page(Integer pageNo, Integer pageSize, PostsPageQueryValue pageQueryValue) {
        PageHelper.startPage(pageNo, pageSize);

        List<PostsDO> postsDOS = postsDAO.queryByValue(pageQueryValue);
        PageInfo<PostsDO> pageInfo = new PageInfo<>(postsDOS);

        if (ObjectUtils.isEmpty(postsDOS)) {
            return PageResult.build(pageInfo.getTotal(), pageInfo.getSize(), new ArrayList<>());
        }

        Set<Long> userIds = SafesUtil.ofList(postsDOS).stream().map(PostsDO::getAuthorId).collect(Collectors.toSet());
        List<User> users = UserTransfer.toUsers(userDAO.queryInIds(userIds));

        Set<Long> postsIds = SafesUtil.ofList(postsDOS).stream().map(PostsDO::getId).collect(Collectors.toSet());
        List<TagPostsMappingDO> tagPostsMappingDOS = tagPostsMappingDAO.queryInPostsIds(postsIds);

        if (ObjectUtils.isEmpty(tagPostsMappingDOS)) {
            List<Article> articles = PostsTransfer.toArticles(postsDOS, users, tagPostsMappingDOS, new ArrayList<>());
            return PageResult.build(pageInfo.getTotal(), pageInfo.getSize(), articles);
        }

        Set<Long> tagIds = SafesUtil.ofList(tagPostsMappingDOS).stream().map(TagPostsMappingDO::getTagId).collect(Collectors.toSet());
        List<Tag> tags = TagTransfer.toTags(tagDAO.queryInIds(tagIds));

        List<Article> articles = PostsTransfer.toArticles(postsDOS, users, tagPostsMappingDOS, tags);

        return PageResult.build(pageInfo.getTotal(), pageInfo.getSize(), articles);
    }

}
