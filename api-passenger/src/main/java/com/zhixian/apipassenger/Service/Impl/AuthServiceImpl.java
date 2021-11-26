package com.zhixian.apipassenger.Service.Impl;

import com.zhixian.apipassenger.Api.PassengerUserAuthApi;
import com.zhixian.apipassenger.Request.UserAuthRequest;
import com.zhixian.apipassenger.Service.AuthService;
import com.zhixian.apipassenger.Service.VerificationCodeService;
import com.zhixian.internalcommon.constant.CommonStatusEnum;
import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicepassengeruser.resquest.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private PassengerUserAuthApi passengerUserAuthApi;
    public ResponseResult auth(UserAuthRequest authRequest) {

    //1.校验验证码
    ResponseResult verificationResult = verificationCodeService.verifyCode(authRequest.getPhoneNumber(), authRequest.getCode());

        if (verificationResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
            return ResponseResult.fail("登入失败，验证码校验失败");

        }
        //登入
        LoginRequest loginRequest = LoginRequest.builder().passengerPhone(authRequest.getPhoneNumber()).build();
        ResponseResult loginResult = passengerUserAuthApi.login(loginRequest);
        if (loginResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
            return ResponseResult.fail("登入失败");
        }
        //3.返会结果
        return loginResult;
    }
}
