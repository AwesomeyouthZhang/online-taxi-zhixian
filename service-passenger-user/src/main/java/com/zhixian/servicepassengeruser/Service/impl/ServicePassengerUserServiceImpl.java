package com.zhixian.servicepassengeruser.Service.impl;

import com.zhixian.internalcommon.constant.RedisKeyExpirationConstant;
import com.zhixian.internalcommon.constant.RedisKeyPrefixConstant;
import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.utils.JwtUtil;
import com.zhixian.servicepassengeruser.Dao.ServicePassengerUserInfoDao;
import com.zhixian.servicepassengeruser.Entity.ServicePassengerUserInfo;
import com.zhixian.servicepassengeruser.Service.ServicePassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 登录、登出接口实现类,
 */
@Service
public class ServicePassengerUserServiceImpl implements ServicePassengerUserService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ServicePassengerUserInfoDao servicePassengerUserInfoDao;

    /**
     *
     * @param passengerPhone
     * @return token
     */
    @Override
    public ResponseResult login(String passengerPhone) {
        //1如果数据库没有该用户，默认进行注册，插库。可以用分布式锁也可以用唯一索引
        //手机号的目的是为了查出该用户的id

        long passengerId;
        //手机号查用户


        ServicePassengerUserInfo servicePassengerUserInfo = servicePassengerUserInfoDao.selectByPhoneNumber(passengerPhone);

        if (!Objects.isNull(servicePassengerUserInfo)){
            passengerId = servicePassengerUserInfo.getId();

        }else {
            ServicePassengerUserInfo newPassenger = new ServicePassengerUserInfo();
            newPassenger.setCreateTime(new Date());
            newPassenger.setGender((byte)1);
            newPassenger.setPassengerName("default");
            newPassenger.setPhoneNumber(passengerPhone);
   /*         servicePassengerUserInfo.builder()
                    .createTime(new Date())
                    .gender((byte)1)
                    .passengerName("default")
                    .phoneNumber(passengerPhone)
                    .build();*/
            //将新用户 插入db
            passengerId = servicePassengerUserInfoDao.insert(newPassenger);
        }
        //用 JWT生成token
        String token = JwtUtil.createToken(String.valueOf(passengerId), new Date());

        //将token存入redis,并设置过去时间
        redisTemplate.opsForValue().set(generateLoginRedisKey(passengerId),
                token,
                RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getDuration(),
                RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getTimeUnit());

        return ResponseResult.success(token);
    }

    @Override
    public ResponseResult logout(Long passengerId) {
        redisTemplate.delete(generateLoginRedisKey(passengerId));
        return ResponseResult.success("logout");
    }
    /**
     * token redis key生成api
     */
    private String generateLoginRedisKey(long passengerId){
        return RedisKeyPrefixConstant.PASSENGER_LOGIN_TOKEN_APP_KEY_PRE + passengerId;
    }
}
