package com.zhixian.serviceverificationcode.Service.impl;

import com.zhixian.internalcommon.constant.CommonStatusEnum;
import com.zhixian.internalcommon.constant.IdentityConstant;
import com.zhixian.internalcommon.constant.RedisKeyExpirationConstant;
import com.zhixian.internalcommon.constant.RedisKeyPrefixConstant;
import com.zhixian.internalcommon.dto.ResponseResult;
import com.zhixian.internalcommon.dto.serviceverifycode.response.VerifyCodeResponse;
import com.zhixian.serviceverificationcode.Entity.VerifyCodeLease;
import com.zhixian.serviceverificationcode.Service.VerifyCodeService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings("all")
public class VerifyCodeServiceImpl implements VerifyCodeService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate1;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public ResponseResult<VerifyCodeService> generate(int identity, String phoneNumber) {
        //校验三档验证

        //用redis 去做限制验证码次数和过期时间

        String errMessage = checkSendCodeTimeLimit(phoneNumber);
        if (!StringUtils.isEmpty(errMessage)){
            return ResponseResult.fail(500, errMessage);

        }

        String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));

        /**
         *  redis key生成
         */

        //根据用户的角色生成key
        String keyPre = generateKeyByIdentity(identity);
        String key = keyPre + phoneNumber;
        //存入redis，并设置60s过期
        redisTemplate.opsForValue().set(key,
                code,
                RedisKeyExpirationConstant.CODE_EXPIRE_TIME_SIXTY_SECONDS.getDuration(),
                RedisKeyExpirationConstant.CODE_EXPIRE_TIME_SIXTY_SECONDS.getTimeUnit()
        );

        //BoundValueOperations<String, String> codeRedis = redisTemplate.boundValueOps(key);
        // 分开做不保证原子性
        // Boolean aBoolean = codeRedis.setIfAbsent(code);
//        if (aBoolean){
//            codeRedis.expire(2,TimeUnit.MINUTES);
//        }

        //codeRedis.set(code, 2, TimeUnit.MINUTES);

        //返回结果

        VerifyCodeResponse data = new VerifyCodeResponse();
        data.setCode(code);
        ResponseResult success = ResponseResult.success(data);


        return success;
    }

    /**
     * 校验验证码三档验证
     * @param identity
     * @param phoneNumber
     * @param code
     * @return
     */


    @Override
    public ResponseResult verify(int identity, String phoneNumber, String code) {
        // 三档验证
        String errMsg = checkSendCodeTimeLimitAbsent(phoneNumber);
        if (!StringUtils.isEmpty(errMsg)) {
            return ResponseResult.fail(500, errMsg);
        }


        String keyPre = generateKeyByIdentity(identity);
        String key = keyPre + phoneNumber;
        String redisCode = (String) redisTemplate.opsForValue().get(key);

        if (StringUtils.isNotBlank(code)
        && StringUtils.isNotBlank(redisCode)
        && code.trim().equals(redisCode.trim())){
            return ResponseResult.success("");

        }else{
            return ResponseResult.fail(CommonStatusEnum.VERIFY_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFY_CODE_ERROR.getValue());

        }
    }

    /**
     * 根据identity生成key
     *
     * @param identity
     * @return
     */
    private String generateKeyByIdentity(int identity) {
        String keyPre = "";
        if (identity == IdentityConstant.PASSENGER) {
            keyPre = RedisKeyPrefixConstant.PASSENGER_LOGIN_CODE_KEY_PRE;
        } else if (identity == IdentityConstant.DRIVER) {
            keyPre = RedisKeyPrefixConstant.DRIVER_LOGIN_CODE_KEY_PRE;

        }
        return keyPre;
    }

    /**
     * 限制发送短信的次数
     * 并返回错误信息
     * @param phoneNumber
     * @return
     */
    public String checkSendCodeTimeLimit(String phoneNumber){
        String isLimited  = checkSendCodeTimeLimitAbsent(phoneNumber);
//        isLimited为空说明时首次
        if (StringUtils.isNotBlank(isLimited)){
            return isLimited;

        }
        //redisLimitationKey redis过期时间key
        String redisLimitationKey = RedisKeyPrefixConstant.SEND_LIMIT_FREQUENT_CODE_KEY_PREFIX.concat(phoneNumber);

        VerifyCodeLease verifyCodeLease = (VerifyCodeLease) redisTemplate.opsForValue().get(redisLimitationKey);


        long currentTimeMillis = System.currentTimeMillis();
        //首次验证码租约为null

        if (Objects.isNull(verifyCodeLease)){
            verifyCodeLease = verifyCodeLease.builder()
                    .codeTimes(1)
                    .fistCodeTime(System.currentTimeMillis())
                    .oneHourLimitation(false)
                    .oneMinLimitation(false)
                    .build();
            StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

            redisTemplate.opsForValue().set(redisLimitationKey,
                    verifyCodeLease,
                    RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getDuration(),
                    RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getTimeUnit());
            return null;

        }else{
            //非首次
         //   System.out.println((currentTimeMillis - verifyCodeLease.()));
           System.out.println((currentTimeMillis - verifyCodeLease.getFistCodeTime()) / (1000 * 60));

            System.out.println(((currentTimeMillis - verifyCodeLease.getFistCodeTime()) / (1000 * 60 * 60)));
            if(verifyCodeLease.getCodeTimes() >= 3
            &&((currentTimeMillis - verifyCodeLease.getFistCodeTime()) / (1000 * 60) <= 1)){
                verifyCodeLease.setOneMinLimitation(true);
                redisTemplate.opsForValue().set(redisLimitationKey,
                        verifyCodeLease,
                        RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getDuration(),
                        RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getTimeUnit());
                return "1分钟法发了三次，限制5分钟不能再发";

            }

            if(verifyCodeLease.getCodeTimes() >= 10
            &&((currentTimeMillis - verifyCodeLease.getFistCodeTime()) / (1000 * 60 * 60) <= 10)){
                verifyCodeLease.setOneHourLimitation(true);
                redisTemplate.opsForValue().set(redisLimitationKey,
                        verifyCodeLease,
                        RedisKeyExpirationConstant.CODE_EXPIRE_TIME_ONE_HOUR.getDuration(),
                        RedisKeyExpirationConstant.CODE_EXPIRE_TIME_ONE_HOUR.getTimeUnit());
                return "10分钟发了10次，限制24小时不能在发短息";
            }

            //次数增加只能在没有限制的条件下才能自增
            if(!verifyCodeLease.isOneHourLimitation() && !verifyCodeLease.isOneMinLimitation()){
                verifyCodeLease.codeTimesIncrements();

            }
            redisTemplate.opsForValue().set(redisLimitationKey,
                    verifyCodeLease,
                    RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getDuration(),
                    RedisKeyExpirationConstant.CODE_EXPIRE_TIME_TEN_MINUTES.getTimeUnit());
            return null;
        }


    }

    /**
     * 判断并返回错误
     * @param phoneNumber
     * @return
     */
    private String checkSendCodeTimeLimitAbsent(String phoneNumber){
        String cacheFreqLimitPhoneKey = RedisKeyPrefixConstant.SEND_LIMIT_FREQUENT_CODE_KEY_PREFIX.concat(phoneNumber);
       // VerifyCodeLease verifyCodeLease = (VerifyCodeLease) redisTemplate.opsForValue().get(cacheFreqLimitPhoneKey);
        VerifyCodeLease lease = (VerifyCodeLease) redisTemplate.opsForValue().get(cacheFreqLimitPhoneKey);
        //首次发送lease为null，返回null
        if(Objects.isNull(lease)){
            return null;
        }

        long currentTimeMillis = System.currentTimeMillis();

        if(lease.isOneMinLimitation()
        &&((currentTimeMillis - lease.getFistCodeTime()) / (1000 * 60) <= 5)
        ){
            return "一分钟发了三次，5分钟内不能再次发送";
        }
        if (lease.isOneHourLimitation()
        && ((currentTimeMillis - lease.getFistCodeTime())/ (1000 * 60 * 60) <= 24)){
            return "一小时发了10次， 限制24小时不能再次发送";

        }
        return null;

    }

}
