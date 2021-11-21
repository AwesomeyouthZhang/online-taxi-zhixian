package com.zhixian.servicesms.service;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicesms.request.SmsSendRequest;
import org.springframework.stereotype.Service;

@Service
public interface SmsService {
    public ResponseResult sendSms(SmsSendRequest smsSendRequest);

}
