package com.example.ai.infra.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmbeddingDTO {
    /**
     * Embedding 信息
     */
    private Float[] embedding;

    /**
     * 下标
     */
    private Long index;

    /**
     * 对象
     */
    private String object;
}
