package com.zhixian.internalcommon.dto;

import com.zhixian.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
//lombok @Accessor chain允许链式编程
@Accessors(chain = true)
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 549836945457685628L;
    private int code;
    private String message;
    private T data;

    /**
     *
     * @param data
     * @param <T>
     * @return 返回成功的数据（status：1）
     */
    public static <T>ResponseResult success(T data){
        return new ResponseResult<>()
                .setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getValue())
                .setData(data);
    }

    /**
     *
     * @param data
     * @param <T>
     * @return 返回错误数据（status：500）
     *
     */
    public static <T> ResponseResult fail(T data){
        return new ResponseResult()
                .setCode(CommonStatusEnum.FAIL.getCode())
                .setMessage(CommonStatusEnum.FAIL.getValue())
                .setData(data);

    }

    /**
     *
     * @param code
     * @param message
     * @param <T>
     * @return 自定义返回错误数据
     */
    public static <T> ResponseResult fail(int code, String message){
        return new ResponseResult()
                .setCode(code)
                .setMessage(message);


    }

    public static <T> ResponseResult fail(T data, String message, int code ){
        return new ResponseResult()
                .setCode(code)
                .setMessage(message)
                .setData(data);

    }


}
