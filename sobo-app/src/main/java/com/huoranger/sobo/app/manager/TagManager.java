package com.huoranger.sobo.app.manager;

import com.huoranger.sobo.app.support.IsLogin;
import com.huoranger.sobo.app.support.PageUtil;
import com.huoranger.sobo.app.transfer.TagTransfer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.api.model.PageRequestModel;
import com.huoranger.sobo.api.model.PageResponseModel;
import com.huoranger.sobo.api.request.AdminBooleanRequest;
import com.huoranger.sobo.api.request.tag.TagCreateRequest;
import com.huoranger.sobo.api.request.tag.TagPageRequest;
import com.huoranger.sobo.api.response.tag.TagPageResponse;
import com.huoranger.sobo.api.response.tag.TagQueryResponse;
import com.huoranger.sobo.api.vo.PostsVO;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.enums.ErrorCodeEn;
import com.huoranger.sobo.common.enums.UserRoleEn;
import com.huoranger.sobo.common.model.PageResult;
import com.huoranger.sobo.common.support.CheckUtil;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.Posts;
import com.huoranger.sobo.domain.entity.Tag;
import com.huoranger.sobo.domain.repository.TagRepository;
import com.huoranger.sobo.domain.service.CacheService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author huoranger
 * @create 2020/7/31
 * @desc
 **/
@Component
public class TagManager extends AbstractPostsManager {

    @Resource
    private TagRepository tagRepository;

    @Resource
    private CacheService cacheService;

    @IsLogin(role = UserRoleEn.ADMIN)
    public void create(TagCreateRequest request) {
        CheckUtil.isNotEmpty(tagRepository.query(Tag.builder().name(request.getName()).build()), ErrorCodeEn.TAG_IS_EXIST);

        tagRepository.save(TagTransfer.toTag(request));
    }

    public List<TagQueryResponse> queryAll() {
        return TagTransfer.toTagQueryAllResponses(tagRepository.queryByState(AuditStateEn.PASS));
    }

    public List<TagQueryResponse> queryInIds(Set<Long> ids) {
        List<Tag> tags = tagRepository.queryByIds(ids);

        return TagTransfer.toTagQueryAllResponses(SafesUtil.ofList(tags).stream()
                .filter(tag -> AuditStateEn.PASS.equals(tag.getAuditState()))
                .collect(Collectors.toList()));
    }

    public TagQueryResponse getByName(String name) {
        Tag tag = tagRepository.getByNameAndState(name, AuditStateEn.PASS);
        CheckUtil.isEmpty(tag, ErrorCodeEn.TAG_NOT_EXIST);

        return TagTransfer.toTagQueryAllResponse(tag);
    }

    public PageResponseModel<PostsVO> pagePosts(PageRequestModel<Long> pageRequestModel) {
        PageResult<Posts> pageResult = tagRepository.pagePosts(PageUtil.buildPageRequest(pageRequestModel, pageRequestModel.getFilter()));

        return pagePostsVO(pageResult);
    }

    public PageResponseModel<PostsVO> pagePostsByTagIds(PageRequestModel<Set<Long>> pageRequestModel) {
        PageResult<Posts> pageResult = tagRepository.pagePostsByTagIds(PageUtil.buildPageRequest(pageRequestModel, pageRequestModel.getFilter()));

        return pagePostsVO(pageResult);
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public PageResponseModel<TagPageResponse> page(PageRequestModel<TagPageRequest> pageRequestModel) {
        TagPageRequest tagPageRequest = pageRequestModel.getFilter();
        Tag tag = Tag.builder()
                .groupName(tagPageRequest.getGroupName())
                .name(tagPageRequest.getName())
                .description(tagPageRequest.getDescription())
                .build();
        if (!ObjectUtils.isEmpty(tagPageRequest.getAuditState())) {
            tag.setAuditState(AuditStateEn.getEntity(tagPageRequest.getAuditState()));
        }

        PageResult<Tag> pageResult = tagRepository.page(PageUtil.buildPageRequest(pageRequestModel, tag));

        return PageResponseModel.build(pageResult.getTotal(), pageResult.getSize(), TagTransfer.toTagPageResponses(pageResult.getList()));
    }

    @IsLogin(role = UserRoleEn.ADMIN)
    public void tagAuditState(AdminBooleanRequest booleanRequest) {
        Tag tag = tagRepository.get(booleanRequest.getId());
        CheckUtil.isEmpty(tag, ErrorCodeEn.TAG_NOT_EXIST);

        tag.setAuditState(booleanRequest.getIs() ? AuditStateEn.PASS : AuditStateEn.REJECT);
        tagRepository.update(tag);
    }

    public List<TagQueryResponse> queryAllRef() {
        List<Tag> tags = tagRepository.queryByState(AuditStateEn.PASS);
        List<Tag> cacheTags = SafesUtil.ofList(tags).stream().filter(tag -> tag.getRefCount() > 0).collect(Collectors.toList());

        return TagTransfer.toTagQueryAllResponses(cacheTags);
    }
}
