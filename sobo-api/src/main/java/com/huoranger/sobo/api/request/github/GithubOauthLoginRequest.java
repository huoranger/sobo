package com.huoranger.sobo.api.request.github;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.huoranger.sobo.api.request.user.UserBaseLoginRequest;

import java.io.Serializable;

/**
 * @author huoranger
 * @create 2021/5/15
 * @desc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubOauthLoginRequest extends UserBaseLoginRequest implements Serializable {

    private String code;
}
