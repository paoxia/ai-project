package com.example.ai.infra.rag;

public interface RagSrv {
    /**
     * 查询rag知识库
     *
     * @param queryContent 查询内容
     * @return 返回结果
     */
    String query(String queryContent);


    /**
     * 构建知识库
     *
     * @param path 路径
     */
    void buildKnowledge(String path);
}
