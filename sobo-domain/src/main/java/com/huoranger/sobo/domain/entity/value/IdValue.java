package com.huoranger.sobo.domain.entity.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huoranger.sobo.common.enums.IdValueTypeEn;

import java.io.Serializable;

/**
 * @author huoranger
 * @create 2020/10/22
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdValue implements Serializable {

    private String id;

    private IdValueTypeEn type;

}
