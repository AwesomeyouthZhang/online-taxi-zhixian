package com.zhixian.apipassenger.Service;

import com.zhixian.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface VerificationCodeService {
    /**
     * 通过手机号发送验证码给手机
     * @param PhoneNumber
     * @return
     */
    ResponseResult send(String PhoneNumber);

    /**
     * 验证码校验
     * @return
     */
    public ResponseResult verifyCode(String phoneNumber, String code);
}
