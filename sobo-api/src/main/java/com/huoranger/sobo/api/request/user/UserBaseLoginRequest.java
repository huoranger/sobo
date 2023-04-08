package com.huoranger.sobo.api.request.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huoranger
 * @create 2020/12/4
 * @desc
 **/
@Data
public class UserBaseLoginRequest implements Serializable {

    private String ua;

    private String ip;

}
