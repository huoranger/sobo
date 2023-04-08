package com.huoranger.sobo.portal.request;

import lombok.Data;

/**
 * @author huoranger
 * @create 2020/10/24
 * @desc
 **/
@Data
public class IndexRequest extends BasePageRequest {

    private String type;

    private String toast;

    private String token;
}
