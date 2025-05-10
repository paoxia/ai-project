package com.example.ai.model.api.service;

public interface HunYuanSrv {

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
    String embedding(String content);
}