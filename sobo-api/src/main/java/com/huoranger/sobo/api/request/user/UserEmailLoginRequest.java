package com.huoranger.sobo.api.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huoranger
 * @create 2020/10/19
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailLoginRequest extends UserBaseLoginRequest implements Serializable {

    private String email;

    private String password;

}
