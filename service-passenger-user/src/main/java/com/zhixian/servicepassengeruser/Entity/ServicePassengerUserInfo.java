package com.zhixian.servicepassengeruser.Entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tbl_passenger_info
 * @author 
 */
@Builder
@ApiModel(value="com.zhixian.servicepassengeruser.Entity.ServicePassengerUserInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicePassengerUserInfo implements Serializable {
    private Integer id;

    /**
     * 电话
     */
    @ApiModelProperty(value="电话")
    private String phoneNumber;

    /**
     * 生日
     */
    @ApiModelProperty(value="生日")
    private Date birthday;

    /**
     * 乘客名称
     */
    @ApiModelProperty(value="乘客名称")
    private String passengerName;

    /**
     * 0：女，1：男
     */
    @ApiModelProperty(value="0：女，1：男")
    private Byte gender;

    /**
     * 头像
     */
    @ApiModelProperty(value="头像")
    private String headImg;

    /**
     * 用户类型，1：个人用户，2：企业用户
     */
    @ApiModelProperty(value="用户类型，1：个人用户，2：企业用户")
    private Byte passengerType;

    /**
     * 注册渠道 1 安卓 2 ios
     */
    @ApiModelProperty(value="注册渠道 1 安卓 2 ios")
    private Short registerType;

    /**
     * 上次登陆方式:1,验证码,2密码
     */
    @ApiModelProperty(value="上次登陆方式:1,验证码,2密码")
    private Byte lastLoginMethod;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}