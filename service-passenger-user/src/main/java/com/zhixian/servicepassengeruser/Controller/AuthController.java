package com.zhixian.servicepassengeruser.Controller;

import com.google.inject.internal.cglib.proxy.$LazyLoader;
import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicepassengeruser.resquest.LoginRequest;
import com.zhixian.servicepassengeruser.Entity.ServicePassengerUserInfo;
import com.zhixian.servicepassengeruser.Service.ServicePassengerUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录。权限校验。登出Controller
 */
@Api("用户登录，权限，登出")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ServicePassengerUserService servicePassengerUserService;

    @ApiOperation("/auth/login")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "loginRequest", value = "loginRequest", dataType = "LoginRequst")

    )
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest){
        return servicePassengerUserService.login(loginRequest.getPassengerPhone());

    }

    @ApiOperation("/auth/logout")
    @ApiImplicitParams(
            @ApiImplicitParam(name= "passengerId", value = "乘客id", dataType = "Long")
    )
    @GetMapping("/logout/{passengerId}")
    public ResponseResult logout(@PathVariable("passengerId") Long passengerId){
        return servicePassengerUserService.logout(passengerId);

    }
}