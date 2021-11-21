package com.zhixian.internalcommon.dto.servicesms.request;

import com.zhixian.internalcommon.dto.servicesms.SmsTemplateDto;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
@Data
public class SmsSendRequest {

    private String[] receivers;
    private List<SmsTemplateDto> data;

    @Override
    public String toString() {
        return "SmsSendRequest [receivers=" + Arrays.toString(receivers) + ", data=" + data + "]";
    }
}
