package com.zhixian.internalcommon.dto.servicesms;

import lombok.Data;

import java.util.Map;

@Data
public class SmsTemplateDto {
    private String id;
    // 参数  占位符
    private Map<String, Object> templateMap;

    @Override
    public String toString() {
        return "SmsTemplateDto [id=" + id + ", templateMap=" + templateMap + "]";
    }

}
