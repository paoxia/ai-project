package com.example.ai.infra.rag.impl;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.ai.common.exception.BizException;
import com.example.ai.infra.model.dto.EmbeddingDTO;
import com.example.ai.infra.model.service.LlmSrv;
import com.example.ai.infra.rag.RagSrv;
import com.example.ai.infra.vectordb.EsVectorSrv;

public class RagSrvImpl implements RagSrv {

    @Autowired
    private LlmSrv llmSrv;
    @Autowired
    private EsVectorSrv esVectorSrv;

    @Override
    public String query(String queryContent) {

        // 将查询向量化
        List<EmbeddingDTO> embeddingDTOList = llmSrv.embedding(queryContent);

        if (CollectionUtils.isEmpty(embeddingDTOList)) {
            throw new BizException("embedding失败");
        }
        EmbeddingDTO embeddingDTO = embeddingDTOList.get(0);
        Float[] queryVector = embeddingDTO.getEmbedding();

        // 查询ES获取相关文案
        List<String> content = esVectorSrv.search(queryVector);

        // 调用相关prompt


        return "";
    }
}
