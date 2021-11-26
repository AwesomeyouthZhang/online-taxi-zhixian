package com.zhixian.apipassenger.Service;

import com.zhixian.apipassenger.Request.UserAuthRequest;
import com.zhixian.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
     ResponseResult auth(UserAuthRequest authRequest);
}
