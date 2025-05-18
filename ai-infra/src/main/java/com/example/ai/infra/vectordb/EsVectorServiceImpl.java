package com.example.ai.infra.vectordb;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

/**
 *
 */
public class EsVectorServiceImpl {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public Boolean insert() {
        // todo implement it
        return false;
    }


    public List<String> search() {
        // todo implement it
        return Collections.EMPTY_LIST;
    }
}
