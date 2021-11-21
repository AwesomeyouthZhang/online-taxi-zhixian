package com.zhixian.servicesms.dao;

import com.zhixian.servicesms.entity.ServiceSmsTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ServiceSmsTemplateCustomDao extends ServiceSmsTemplateDao{
    ServiceSmsTemplate selectByTemplateId(String templateId);
}
