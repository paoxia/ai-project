package com.example.ai.infra.vectordb;

import java.util.List;

// todo 抽象成公共接口
public interface EsVectorSrv {

    /**
     * 插入数据
     *
     * @return 返回是否成功
     */
    Boolean insert();

    List<String> search(Float[] vector);
}
