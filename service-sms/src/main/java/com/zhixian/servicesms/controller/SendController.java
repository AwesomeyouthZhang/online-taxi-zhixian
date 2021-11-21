package com.zhixian.servicesms.controller;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.zhixian.servicesms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
@Api(value = "SERVICE-SMS验证码发送")
@RestController
@RequestMapping("/send")
@Slf4j
public class SendController {
    @Autowired
    private SmsService smsService;
    @RequestMapping(value = "/sms-template", method = RequestMethod.POST)
    @ApiOperation(value = "发送短信")
    public ResponseResult send(@RequestBody SmsSendRequest smsSendRequest){
        //通过输出参数内容落地日志
        JSONObject param = JSONObject.fromObject(smsSendRequest);

        log.info("/send/alisms-template   request："+param.toString());
        return smsService.sendSms(smsSendRequest);


    }

}
