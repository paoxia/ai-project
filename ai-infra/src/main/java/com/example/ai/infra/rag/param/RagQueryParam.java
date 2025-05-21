package com.example.ai.infra.rag.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RagQueryParam {
    /**
     * 查询内容
     */
    private String queryContent;
}
