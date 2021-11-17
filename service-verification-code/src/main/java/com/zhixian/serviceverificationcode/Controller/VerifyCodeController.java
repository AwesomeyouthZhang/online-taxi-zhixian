package com.zhixian.serviceverificationcode.Controller;

import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.serviceverificationcode.Service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify-code")
public class VerifyCodeController {
    @Autowired
    VerifyCodeService verifyCodeService;

    @GetMapping("/generate/{identity}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identity") int identity, @PathVariable("phoneNumber") int phoneNumber){
        return verifyCodeService.generate(identity,phoneNumber);


    }
}
