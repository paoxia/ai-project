package com.example.ai.infra.vectordb;

import java.util.List;

/**
 * ES向量库索引
 */
public interface EsVectorSrv {

    /**
     * 插入数据
     *
     * @return 返回是否成功
     */
    Boolean insert();

    /**
     * 查询向量
     *
     * @param vector 向量
     * @return 相关语句
     */
    List<String> search(Float[] vector);
}
