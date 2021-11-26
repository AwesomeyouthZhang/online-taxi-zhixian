package com.zhixian.apipassenger.Api;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.serviceverifycode.request.VerifyCodeRequest;
import io.swagger.annotations.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * feign验证码service-verification-code调用类
 */
@Api("feign验证码service-verification-code调用类")
@FeignClient(name = "service-verification-code")
public interface CodeServiceApi {
    @ApiOperation("/generate")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="identify", value = "用户身份", dataType = "Integer"),
            @ApiImplicitParam(name = "phoneNumber", value = "手机号", dataType = "Integer")
    })
    @GetMapping("/verify-code/generate/{identify}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identify") int identify, @PathVariable("phoneNumber") String phoneNumber);


    @ApiOperation("验证验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", dataType = "VerifyCodeRequest")
    })
    @PostMapping("/verify-code/verify")
    public ResponseResult verify(@RequestBody VerifyCodeRequest request);

}
