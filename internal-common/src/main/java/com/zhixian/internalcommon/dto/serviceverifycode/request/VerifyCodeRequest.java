package com.zhixian.internalcommon.dto.serviceverifycode.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("VerifyCodeRequest实体类")
public class VerifyCodeRequest {

    @ApiModelProperty("用户角色")
    private int identity;
    @ApiModelProperty("用户手机号")
    private String phoneNumber;
    @ApiModelProperty("验证码")
    private String code;
}
