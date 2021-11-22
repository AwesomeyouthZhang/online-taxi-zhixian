package com.zhixian.serviceverificationcode.Entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
@Data
@SuppressWarnings("all")
@Builder
public class VerifyCodeLease implements Serializable {
    private static final long serialVersionUID = -97256688374003884L;

    //验证码获取的次数
    private long codeTimes;

    //第一次获取验证码的时间
    private long fistCodeTime;

    //60s限制开关
    private boolean oneMinLimitation;

    //1hour限制开关
    private boolean oneHourLimitation;

    public void codeTimesIncrements(){
        codeTimes += 1;
    }
}
