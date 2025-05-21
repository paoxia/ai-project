package com.example.ai.infra.rag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 法律ES结构
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LawES {
    /**
     * 内容
     */
    private String content;

    /**
     * 向量
     */
    private Float[] vector;
}
