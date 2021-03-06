package com.zhixian.apipassenger.Request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户授权请求接受体
 */
@Data
@Builder
public class UserAuthRequest {

    @NotNull(message = "手机号不能为空")
    @Pattern(message = "手机校验不正确"
            , regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$")
    private String phoneNumber;

    @NotNull(message = "验证码不可为空")
    private String code;

}
