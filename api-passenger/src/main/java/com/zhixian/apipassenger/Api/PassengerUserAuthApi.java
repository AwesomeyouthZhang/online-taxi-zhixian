package com.zhixian.apipassenger.Api;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.servicepassengeruser.resquest.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 乘客用户授权登录api
 */
@Api("乘客用户授权登录api")
@FeignClient("service-passenger-user")
public interface PassengerUserAuthApi {
    @GetMapping("/auth/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "passengerId", value = "用户Id", dataType = "Long")
    })
    @ApiOperation("用户退出登入")
    public ResponseResult logout( long passengerId);

    @PostMapping("/auth/login")
    @ApiOperation("用户登入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginRequest", value = "登入请求实体类", dataType = "LoginRequest")
    })
    public ResponseResult login(@RequestBody LoginRequest loginRequest);


}
