package com.zhixian.internalcommon.constant;

public class RedisKeyPrefixConstant {

    /**
     * 验证码次数限制 key前缀
     */
    public static String SEND_LIMIT_FREQUENT_CODE_KEY_PREFIX = "send_limit_freq_code_";

    /**
     * 乘客登录验证码 key前缀
     */
    public static final String PASSENGER_LOGIN_CODE_KEY_PRE = "passenger_login_code_";

    public static final String PASSENGER_LOGIN_TOKEN_APP_KEY_PRE = "passenger_login_token_app_";

    public static final String PASSENGER_LOGIN_TOKEN_WEIXIN_KEY_PRE = "passenger_login_token_weixin_";

    /**
     * 司机登录验证码 key前缀
     */
    public static final String DRIVER_LOGIN_CODE_KEY_PRE = "driver_login_code_";
}
