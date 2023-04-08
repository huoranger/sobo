package com.huoranger.sobo.app.support;

import lombok.Data;
import com.huoranger.sobo.common.support.SafesUtil;
import com.huoranger.sobo.domain.entity.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * @author huoranger
 * @create 2020/11/4
 * @desc
 **/
@Data
public class Pair<T> {

    private T value0;

    private T value1;

    public static <T> Pair<T> build(T value0, T value1) {
        Pair pair = new Pair();
        pair.setValue0(value0);
        pair.setValue1(value1);

        return pair;
    }

    public static Set<Long> diff(Set<Tag> ones, Set<Tag> twos) {
        Set<Long> diffTags = new HashSet<>();

        SafesUtil.ofSet(ones).forEach(oldTag -> {
            boolean has = false;
            for (Tag tag : twos) {
                if (tag.getId().equals(oldTag.getId())) {
                    has = true;
                }
            }
            if (!has) {
                diffTags.add(oldTag.getId());
            }
        });

        return diffTags;
    }

    public static Set<Long> tagToLong(Set<Tag> ones) {
        Set<Long> tags = new HashSet<>();
        SafesUtil.ofSet(ones).forEach(oldTag -> {
            tags.add(oldTag.getId());
        });
        return tags;
    }
}
