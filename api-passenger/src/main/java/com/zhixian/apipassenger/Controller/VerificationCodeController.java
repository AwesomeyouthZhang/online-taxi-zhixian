package com.zhixian.apipassenger.Controller;

import com.zhixian.apipassenger.Request.ShortMessageRequest;
import com.zhixian.apipassenger.Service.VerificationCodeService;
import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicesms.SmsTemplateDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.ShortMessage;

/**
 * 用户发送验证码
 */
@RestController
@RequestMapping("/verify-code")
@Api(value = "验证码发送")
public class VerificationCodeController{
    @Autowired
    private VerificationCodeService verificationCodeService;
    @ApiOperation("发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ShortMessageRequest", value = "ShortMessageRequest", required = true, dataType = "ShortMessageRequest"),

    })
    @PostMapping("/send")
    public ResponseResult sendVerificationCode(@RequestBody @Validated ShortMessageRequest shortMessage){
        return verificationCodeService.send(shortMessage.getPhoneNumber());

    }


}
