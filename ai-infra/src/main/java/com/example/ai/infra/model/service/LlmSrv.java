package com.example.ai.infra.model.service;

import java.util.List;
import com.example.ai.infra.model.dto.EmbeddingDTO;

public interface LlmSrv {

    /**
     * 聊天
     *
     * @param content 输入内容
     * @return 聊天回答
     */
    String chat(String content);

    /**
     * 翻译
     *
     * @param content 输入内容
     * @return 翻译结果
     */
    String translate(String content);

    /**
     * 向量化
     *
     * @param content 输入内容
     * @return 向量化结果
     */
    List<EmbeddingDTO> embedding(String content);
}