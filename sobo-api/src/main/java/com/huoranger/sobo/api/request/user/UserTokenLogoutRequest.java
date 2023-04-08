package com.huoranger.sobo.api.request.user;

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
public class UserTokenLogoutRequest extends UserBaseLoginRequest {

    private String token;

}
