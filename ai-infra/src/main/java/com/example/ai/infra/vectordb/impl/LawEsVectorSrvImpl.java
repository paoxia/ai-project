package com.example.ai.infra.vectordb.impl;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ai.infra.vectordb.EsVectorSrv;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

/**
 * 向量服务
 */
@Service("lawEsVectorSrv")
public class LawEsVectorSrvImpl implements EsVectorSrv {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public Boolean insert() {
        // todo implement it
        return false;
    }

    @Override
    public List<String> search(Float[] vector) {
        // todo implement it
        return Collections.EMPTY_LIST;
    }
}
