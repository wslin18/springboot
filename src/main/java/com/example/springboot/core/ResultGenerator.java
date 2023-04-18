package com.example.springboot.core;

/**
 * @author wang
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 不带参数返回成功
     * @return
     */
    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 带参数返回成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    /**
     * 失败返回
     * @param message
     * @return
     */
    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    /**
     * 资源不存在的返回
     * @return
     */
    public static Result genNotFoundResult(String message) {
        return new Result().setCode(ResultCode.NOT_FOUND)
                .setMessage(message);
    }
}
