package com.example.ai.common.utils;

import java.util.Objects;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类
 */
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 转化成json string
     *
     * @param o 对象
     * @return json string
     */
    public static String toJsonString(Object o) {

        if (Objects.isNull(o)) {
            return null;
        }
        // 是string的话直接返回
        if (o instanceof String) {
            return o.toString();
        }

        try {
            return mapper.writeValueAsString(o);
        } catch (Exception ignored) {

        }
        return null;
    }
}
