package com.example.ai.common.exception;


import lombok.Getter;


/**
 * 参数校验错误
 */
@Getter
public class ParamException extends RuntimeException {

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
