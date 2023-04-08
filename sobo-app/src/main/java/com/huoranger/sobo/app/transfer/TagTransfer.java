package com.huoranger.sobo.app.transfer;

import com.huoranger.sobo.api.request.tag.TagCreateRequest;
import com.huoranger.sobo.api.response.tag.TagPageResponse;
import com.huoranger.sobo.api.response.tag.TagQueryResponse;
import com.huoranger.sobo.app.support.LoginUserContext;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.support.DateUtil;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huoranger
 * @create 20/9/9
 * @desc
 **/
public class TagTransfer {

    public static Tag toTag(TagCreateRequest request) {
        return Tag.builder()
                .groupName(request.getGroupName())
                .auditState(AuditStateEn.WAIT)
                .creatorId(LoginUserContext.getUser().getId())
                .description(request.getDescription())
                .name(request.getName())
                .refCount(0L)
                .build();
    }

    public static List<TagQueryResponse> toTagQueryAllResponses(List<Tag> tags) {
        List<TagQueryResponse> responses = new ArrayList<>();

        SafesUtil.ofList(tags).forEach(tag -> responses.add(toTagQueryAllResponse(tag)));
        return responses;
    }

    public static TagQueryResponse toTagQueryAllResponse(Tag tag) {
        return TagQueryResponse.builder()
                .description(tag.getDescription())
                .groupName(tag.getGroupName())
                .id(tag.getId())
                .name(tag.getName())
                .refCount(tag.getRefCount())
                .build();
    }

    public static List<TagPageResponse> toTagPageResponses(List<Tag> tags) {
        List<TagPageResponse> responses = new ArrayList<>();

        SafesUtil.ofList(tags).forEach(tag -> responses.add(TagPageResponse.builder()
                .auditState(tag.getAuditState().getDesc())
                .createAt(DateUtil.toyyyyMMddHHmmss(tag.getCreateAt()))
                .creatorId(tag.getCreatorId())
                .description(tag.getDescription())
                .groupName(tag.getGroupName())
                .id(tag.getId())
                .name(tag.getName())
                .refCount(tag.getRefCount())
                .updateAt(DateUtil.toyyyyMMddHHmmss(tag.getUpdateAt()))
                .build()));

        return responses;
    }
}
