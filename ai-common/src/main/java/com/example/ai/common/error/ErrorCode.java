package com.example.ai.common.error;

/**
 * 错误码
 */
public interface ErrorCode {

    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMsg();
}
