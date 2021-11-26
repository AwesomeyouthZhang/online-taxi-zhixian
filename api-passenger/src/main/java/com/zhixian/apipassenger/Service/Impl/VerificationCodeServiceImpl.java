package com.zhixian.apipassenger.Service.Impl;

import com.zhixian.apipassenger.Api.CodeServiceApi;
import com.zhixian.apipassenger.Api.SmsServiceApi;
import com.zhixian.apipassenger.Service.VerificationCodeService;
import com.zhixian.internalcommon.constant.CommonStatusEnum;
import com.zhixian.internalcommon.constant.IdentityConstant;
import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicesms.SmsTemplateDto;
import com.zhixian.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.zhixian.internalcommon.dto.serviceverifycode.request.VerifyCodeRequest;
import com.zhixian.internalcommon.dto.serviceverifycode.response.VerifyCodeResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService
{
    @Autowired
    CodeServiceApi codeServiceApi;

    @Autowired
    SmsServiceApi smsServiceApi;


    @Override
    public ResponseResult send(String phoneNumber) {
        //1.调用service-verification-code模块生成验证码
        ResponseResult generateCodeResponse = codeServiceApi.generate(IdentityConstant.PASSENGER, phoneNumber);

        VerifyCodeResponse verifyCodeResponse = null;
        if(generateCodeResponse.getCode() == CommonStatusEnum.SUCCESS.getCode()){
            verifyCodeResponse = JSON.parseObject(JSON.toJSONString(generateCodeResponse.getData()), VerifyCodeResponse.class);

        }else{
            return ResponseResult.fail("获取验证码失败！");
        }
        String code = verifyCodeResponse.getCode();

        //2.调用service-sms.send向乘客发送验证码

        ResponseResult sendResult = smsServiceApi.send(buildSmsSendRequest(phoneNumber, code));
        if(sendResult.getCode() != CommonStatusEnum.SUCCESS.getCode()){
            return ResponseResult.fail("发送短信失败");
        }
        log.info("用户："+ phoneNumber +"发送验证码："+ code);


        return ResponseResult.success("");
    }

    /**
     * 构建smsSendRequest，供send（）使用
     * @param phoneNumber
     * @param code
     * @return
     */
    private SmsSendRequest buildSmsSendRequest(String phoneNumber, String code){
        ArrayList<String> receivers = new ArrayList<>();
        ArrayList<SmsTemplateDto> smsTemplateDtoData = new ArrayList<>();
        receivers.add(phoneNumber);

        //消息模板构建
        HashMap<String, Object> templateMap = new HashMap<>();
        templateMap.put("code", Integer.valueOf(code));

        smsTemplateDtoData.add(SmsTemplateDto.builder()
                .id("SMS_144145499")
                .templateMap(templateMap)
                .build());
        return SmsSendRequest.builder().receivers(receivers).data(smsTemplateDtoData).build();
    }

    @Override
    public ResponseResult verifyCode(String phoneNumber, String code) {
        VerifyCodeRequest codeRequest = VerifyCodeRequest.builder()
                .code(code)
                .identity(IdentityConstant.PASSENGER)
                .phoneNumber(phoneNumber)
                .build();


        return codeServiceApi.verify(codeRequest);
    }
}
