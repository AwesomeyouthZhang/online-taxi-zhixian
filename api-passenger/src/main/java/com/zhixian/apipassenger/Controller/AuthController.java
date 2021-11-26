package com.zhixian.apipassenger.Controller;

import com.zhixian.apipassenger.Api.PassengerUserAuthApi;
import com.zhixian.apipassenger.Request.UserAuthRequest;
import com.zhixian.apipassenger.Service.AuthService;
import com.zhixian.apipassenger.Service.VerificationCodeService;
import com.zhixian.internalcommon.constant.CommonStatusEnum;
import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicepassengeruser.resquest.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api("api-passenger授权Controller")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    @ApiOperation("api-passenger授权")
    @ApiImplicitParam(name = "authRequest", value = "用户授权请求接受体", dataType = "UserAuthRequest")
    public ResponseResult auth(@RequestBody @Validated UserAuthRequest authRequest){

        return authService.auth(authRequest);


    }

}
