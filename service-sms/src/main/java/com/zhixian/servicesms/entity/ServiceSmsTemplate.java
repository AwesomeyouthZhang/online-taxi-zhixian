package com.zhixian.servicesms.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */

@Data
public class ServiceSmsTemplate implements Serializable {
    private Integer id;
    private static final long serialVersionUID = -3815050057238044869L;

    /**
     * 短信模板ID
     */
    private String templateId;

    private String templateName;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 模板类型（1：营销；2：通知；3：订单）
     */
    private Boolean templateType;

    private Date createTime;

    private Date updateTime;


}
