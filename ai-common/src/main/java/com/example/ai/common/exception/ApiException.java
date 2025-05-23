package com.example.ai.common.exception;


import com.example.ai.common.error.ErrorCode;

import lombok.Getter;

/**
 * Api异常
 */
@Getter
public class ApiException extends RuntimeException {

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMsg());
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

}