package com.zhixian.internalcommon.dto.servicepassengeruser.resquest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class LoginRequest {
    private String passengerPhone;
}
