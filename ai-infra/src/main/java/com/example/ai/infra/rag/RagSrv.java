package com.example.ai.infra.rag;

import java.util.List;

public interface RagSrv {
    /**
     * 构建知识库
     *
     * @param path 路径
     */
    void buildKnowledge(String path);


    /**
     * 查询rag知识库
     *
     * @param queryContent 查询内容
     * @return 返回结果
     */
    List<String> query(String queryContent);

    /**
     * 查询tag知识库并调用大模型
     *
     * @param queryContent 查询内容
     * @return 返回结果
     */
    String chat(String queryContent);
}
