package com.zhixian.serviceverificationcode.Service.impl;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.serviceverifycode.response.VerifyCodeResponse;
import com.zhixian.serviceverificationcode.Service.VerifyCodeService;
import org.springframework.stereotype.Service;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    @Override
    public ResponseResult<VerifyCodeService> generate(int identity, int phoneNumber) {
        //校验三档验证
        //用redis 去做限制验证码次数和过期时间
        String code = String.valueOf((int)((Math.random()*9+1)*Math.pow(10,5)));
        VerifyCodeResponse data = new VerifyCodeResponse();
        data.setCode(code);
        ResponseResult success = ResponseResult.success(data);


        return success;
    }
}
