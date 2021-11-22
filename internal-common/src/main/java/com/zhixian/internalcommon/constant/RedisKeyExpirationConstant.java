package com.zhixian.internalcommon.constant;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * Redis过期时间常量类
 * 正常过期时间为60s
 * 验证码生成次数受限
 * 在验证码的生成过程中使用受限, 根据需求: 1分钟三次,5分钟生成受限;1小时10次,24小时受限.
 * 所以验证码的有效期至少为1小时
 */

@Getter
public enum RedisKeyExpirationConstant {
    CODE_EXPIRE_TIME_SIXTY_SECONDS (60, TimeUnit.SECONDS),
    CODE_EXPIRE_TIME_TEN_MINUTES(10, TimeUnit.MINUTES),
    CODE_EXPIRE_TIME_ONE_HOUR(1, TimeUnit.HOURS);

    int duration;
    TimeUnit timeUnit;

    RedisKeyExpirationConstant(int duration, TimeUnit timeUnit) {
        this.duration = duration;
        this.timeUnit = timeUnit;
    }
}
