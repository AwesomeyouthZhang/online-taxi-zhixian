package com.zhixian.internalcommon.dto.servicesms.request;

import com.zhixian.internalcommon.dto.servicesms.SmsTemplateDto;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
@Data
@Builder
public class SmsSendRequest {

    private List<String> receivers;
    private List<SmsTemplateDto> data;

    @Override
    public String toString() {
        return "SmsSendRequest [receivers=" + receivers.toString() + ", data=" + data + "]";
    }
}
