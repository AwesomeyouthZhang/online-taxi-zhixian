package com.zhixian.apipassenger.Service;

import com.zhixian.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Component;

@Component
public interface VerificationCodeService {
    ResponseResult send();
}
