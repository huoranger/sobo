package com.huoranger.sobo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huoranger.sobo.common.enums.SearchTypeEn;

/**
 * @author huoranger
 * @create 2020/12/2
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Search extends BaseEntity {

    private SearchTypeEn type;

    private Long entityId;

    private String title;

    private String content;

}
