package com.example.ai.common.utils;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import com.example.ai.common.exception.ParamException;

/**
 * 参数校验工具类
 */
public class ParamCheckUtils {
    /**
     * 校验字符串是否非空
     *
     * @param str    字符串
     * @param errMsg 错误msg
     */
    public static void checkStrNotBlank(String str, String errMsg) {
        if (StringUtils.isBlank(str)) {
            throw new ParamException(errMsg);
        }
    }


    /**
     * 校验对象是否不是null
     *
     * @param obj    对象
     * @param errMsg 错误msg
     */
    public static void checkObjNotNull(Object obj, String errMsg) {
        if (Objects.isNull(obj)) {
            throw new ParamException(errMsg);
        }
    }

}
