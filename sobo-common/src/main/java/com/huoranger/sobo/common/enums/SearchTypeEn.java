package com.huoranger.sobo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Getter
@AllArgsConstructor
public enum SearchTypeEn {
    POSTS("POSTS", "帖子"),
    ;

    private String value;
    private String desc;
}
