package com.huoranger.sobo.infrastructure.dal.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huoranger
 * @create 20/7/23
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CacheDO extends BaseDO {

    private String type;

    private String key;

    private String value;

}
