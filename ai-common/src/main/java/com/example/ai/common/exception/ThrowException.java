package com.example.ai.common.exception;

/**
 * 抛出错误
 */
public class ThrowException {

    /**
     * 抛出参数异常
     *
     * @param msg 异常信息
     */
    public static void throwParamException(String msg) {
        throw new ParamException(msg);
    }

    /**
     * 抛出业务异常
     *
     * @param msg 异常信息
     */
    public static void throwBizException(String msg) {
        throw new BizException(msg);
    }
}
