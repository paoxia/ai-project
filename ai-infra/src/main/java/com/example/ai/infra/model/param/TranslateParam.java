package com.example.ai.infra.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 翻译入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslateParam {
    /**
     * 原文
     */
    private String content;
}
