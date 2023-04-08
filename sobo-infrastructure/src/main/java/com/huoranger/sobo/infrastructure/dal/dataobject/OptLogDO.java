package com.huoranger.sobo.infrastructure.dal.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huoranger
 * @create 2020/12/4
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptLogDO extends BaseDO {

    private String type;

    private Long operatorId;

    private String content;
}
