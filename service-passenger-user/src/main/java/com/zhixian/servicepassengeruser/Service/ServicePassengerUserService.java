package com.zhixian.servicepassengeruser.Service;

import com.zhixian.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 *登录、登出接口
 */
@Service
public interface ServicePassengerUserService {
    ResponseResult login(String passengerPhone);

    ResponseResult logout(Long passengerId);
}
