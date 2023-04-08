package com.huoranger.sobo.infrastructure.transfer;

import org.springframework.util.ObjectUtils;
import com.huoranger.sobo.common.enums.AuditStateEn;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.Faq;
import com.huoranger.sobo.domain.entity.Tag;
import com.huoranger.sobo.infrastructure.dal.dataobject.PostsDO;
import com.huoranger.sobo.infrastructure.dal.dataobject.TagDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huoranger
 * @create 2020/10/31
 * @desc
 **/
public class TagTransfer {

    public static List<Faq> toFaqs(List<PostsDO> postsDOS) {
        List<Faq> faqs = new ArrayList<>();

        SafesUtil.ofList(postsDOS).forEach(postsDO -> {
            Faq faq = new Faq();
            faq.setId(postsDO.getId());
            faq.setTitle(postsDO.getTitle());
            faq.setCreateAt(postsDO.getCreateAt());

            faqs.add(faq);
        });

        return faqs;
    }

    public static TagDO toTagDO(Tag tag) {
        TagDO tagDO = TagDO.builder()
                .auditState(ObjectUtils.isEmpty(tag.getAuditState()) ? null : tag.getAuditState().getValue())
                .creatorId(tag.getCreatorId())
                .groupName(tag.getGroupName())
                .description(tag.getDescription())
                .name(tag.getName())
                .refCount(tag.getRefCount())
                .build();

        tagDO.initBase();

        return tagDO;
    }

    public static List<Tag> toTags(List<TagDO> tagDOS) {
        List<Tag> tags = new ArrayList<>();

        SafesUtil.ofList(tagDOS).forEach(tagDO -> tags.add(toTag(tagDO)));

        return tags;
    }

    public static Tag toTag(TagDO tagDO) {
        if (ObjectUtils.isEmpty(tagDO)) {
            return null;
        }

        Tag tag = Tag.builder()
                .auditState(AuditStateEn.getEntity(tagDO.getAuditState()))
                .groupName(tagDO.getGroupName())
                .creatorId(tagDO.getCreatorId())
                .description(tagDO.getDescription())
                .name(tagDO.getName())
                .refCount(tagDO.getRefCount())
                .build();
        tag.setId(tagDO.getId());
        tag.setCreatorId(tagDO.getCreatorId());
        tag.setCreateAt(tagDO.getCreateAt());
        tag.setUpdateAt(tagDO.getUpdateAt());

        return tag;
    }

}
