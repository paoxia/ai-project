package com.example.ai.infra.rag.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import com.example.ai.common.exception.BizException;
import com.example.ai.common.utils.JsonUtils;
import com.example.ai.infra.model.dto.EmbeddingDTO;
import com.example.ai.infra.model.service.LlmSrv;
import com.example.ai.infra.rag.RagSrv;
import com.example.ai.infra.vectordb.EsVectorSrv;

/**
 * 法律rag
 */
@Service("lawRagSrv")
public class LawRagSrvImpl implements RagSrv {

    private static final Logger logger = LoggerFactory.getLogger(LawRagSrvImpl.class);

    @Autowired
    private LlmSrv llmSrv;
    @Autowired
    @Qualifier("lawEsVectorSrv")
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

        // 查询ES向量库获取相关文案
        List<String> content = esVectorSrv.search(queryVector);

        // 调用相关prompt


        return "";
    }

    @Override
    public void buildKnowledge(String path) {

        try {

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:" + path + "/**/*");

            List<String> fileNames = new ArrayList<>();
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    String uri = resource.getURI().toString();
                    fileNames.add(uri);
                }
            }
            logger.info("查询到以下法律文件{}", JsonUtils.toJsonString(fileNames));

            // esVectorSrv.insert()

        } catch (IOException e) {
            logger.error("查询资源文件失败", e);
        } catch (Exception e) {
            logger.error("构建知识库失败", e);
        }

    }
}
