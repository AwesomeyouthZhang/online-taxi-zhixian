package com.zhixian.serviceverificationcode.Controller;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.serviceverifycode.request.VerifyCodeRequest;
import com.zhixian.serviceverificationcode.Service.VerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify-code")
@Slf4j
@Api(value = "验证码生成和校验")
public class VerifyCodeController {
    @Autowired
    VerifyCodeService verifyCodeService;
    @ApiOperation(value = "根据手机号验证码生成")
    @GetMapping("/generate/{identity}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identity") int identity, @PathVariable("phoneNumber") int phoneNumber) {
//        日志落地
        log.info("/generate/{identity}/{phoneNumber} ： 身份类型：" + identity + ",手机号：" + phoneNumber);

        return verifyCodeService.generate(identity,String.valueOf(phoneNumber));

    }

    @PostMapping("/verify")
    @ApiOperation("校验验证码")
    public ResponseResult verify(@RequestBody VerifyCodeRequest request){
        //日志落地
        log.info("/verify-code/verify  request:"+ JSONObject.fromObject(request));
        //获取用户登录的手机号
        String phoneNumber = request.getPhoneNumber();
        int identity = request.getIdentity();
        String code = request.getCode();

        return verifyCodeService.verify(identity, phoneNumber, code);
    }
}
