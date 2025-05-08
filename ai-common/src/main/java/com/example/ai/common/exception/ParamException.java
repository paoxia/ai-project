package com.example.ai.common.exception;



import com.example.ai.common.error.ErrorCode;

import lombok.Getter;

import static com.example.ai.common.error.ResultCode.VALIDATE_FAILED;


/**
 * 参数校验错误
 */
@Getter
public class ParamException extends RuntimeException {

    private final ErrorCode errorCode = VALIDATE_FAILED;


    public ParamException(String message) {
        super(message);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

}
