package com.example.ai.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 向量化参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmbeddingParam {
    /**
     * 原文
     */
    private String content;
}