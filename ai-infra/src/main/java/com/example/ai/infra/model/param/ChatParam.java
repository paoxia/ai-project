package com.example.ai.infra.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatParam {
    /**
     * 提问内容
     */
    private String content;
}
