package com.example.ai.common.exception;



import com.example.ai.common.error.ErrorCode;

import lombok.Getter;

import static com.example.ai.common.error.ResultCode.BIZ_ERROR;


/**
 * 业务异常
 */
@Getter
public class BizException extends RuntimeException {

    private final ErrorCode errorCode = BIZ_ERROR;


    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

}
