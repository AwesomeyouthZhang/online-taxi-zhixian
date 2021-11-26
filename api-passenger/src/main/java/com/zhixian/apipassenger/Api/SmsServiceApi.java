package com.zhixian.apipassenger.Api;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicesms.request.SmsSendRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Api("service-sms短信服务api")
@FeignClient(name = "service-sms")
public interface SmsServiceApi {

    @ApiOperation("发送短信")
    @PostMapping("/send/sms-template")
    public ResponseResult send(@RequestBody SmsSendRequest smsTemplate);
}
