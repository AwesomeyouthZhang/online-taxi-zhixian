package com.zhixian.serviceverificationcode.Service;

import com.zhixian.internalcommon.dto.ResponseResult;

public interface VerifyCodeService {
    /**
     * 根据手机号生成验证码
     * @param identity
     * @param phoneNumber
     * @return
     */
    public ResponseResult<VerifyCodeService> generate(int identity, String phoneNumber);

    /**
     * 校验 身份， 手机号， 验证码的合法性
     * @param identity
     * @param phoneNumber
     * @param code
     * @return
     */

    ResponseResult verify(int identity, String phoneNumber, String code);
}
