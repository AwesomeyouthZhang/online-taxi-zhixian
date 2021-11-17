package com.zhixian.serviceverificationcode.Service;

import com.zhixian.internalcommon.dto.ResponseResult;

public interface VerifyCodeService {
    public ResponseResult<VerifyCodeService> generate(int identity, int phoneNumber);
}
