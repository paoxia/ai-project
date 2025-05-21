package com.example.ai.infra.rag.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import com.example.ai.common.exception.BizException;
import com.example.ai.infra.chunk.ChunkUtils;
import com.example.ai.infra.model.dto.EmbeddingDTO;
import com.example.ai.infra.model.service.LlmSrv;
import com.example.ai.infra.prompt.PromptConstant;
import com.example.ai.infra.rag.RagSrv;
import com.example.ai.infra.rag.model.LawES;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

/**
 * 法律rag
 */
@Service("lawRagSrv")
public class LawRagSrvImpl implements RagSrv {

    private static final Logger logger = LoggerFactory.getLogger(LawRagSrvImpl.class);

    @Autowired
    @Qualifier("hunyuanSrv")
    private LlmSrv llmSrv;


    @Autowired
    private ElasticsearchClient esClient;
    /**
     * law rag索引
     */
    private final static String INDEX = "rag-law-index";

    @Override
    public List<String> query(String queryContent) {
        // 将查询向量化
        List<EmbeddingDTO> embeddingDTOList = llmSrv.embedding(queryContent);

        if (CollectionUtils.isEmpty(embeddingDTOList)) {
            throw new BizException("embedding失败");
        }
        EmbeddingDTO embeddingDTO = embeddingDTOList.get(0);
        Float[] queryVector = embeddingDTO.getEmbedding();

        try {
            // 查询ES向量库获取相关文案
            SearchResponse<LawES> response = esClient.search(s -> s
                            .index(INDEX)
                            .knn(knn -> knn
                                    .field("vector")
                                    .queryVector(Arrays.stream(queryVector)
                                            .toList())
                                    .k(3)
                                    .numCandidates(100)
                            ),
                    LawES.class
            );

            if (response == null || response.hits() == null || response.hits().hits() == null) {
                return null;
            }
            List<String> content = new ArrayList<>();
            for (Hit<LawES> hit : response.hits().hits()) {
                if (hit.source() != null && StringUtils.isNotBlank(hit.source().getContent())) {
                    content.add(hit.source().getContent());
                }
            }
            return content;
        } catch (Exception e) {
            logger.error("查询知识库失败", e);
        }

        return null;
    }

    @Override
    public String chat(String queryContent) {
        // 将查询向量化
        List<EmbeddingDTO> embeddingDTOList = llmSrv.embedding(queryContent);

        if (CollectionUtils.isEmpty(embeddingDTOList)) {
            throw new BizException("embedding失败");
        }
        EmbeddingDTO embeddingDTO = embeddingDTOList.get(0);
        Float[] queryVector = embeddingDTO.getEmbedding();

        try {
            // 查询ES向量库获取相关文案
            SearchResponse<LawES> response = esClient.search(s -> s
                            .index(INDEX)
                            .knn(knn -> knn
                                    .field("vector")
                                    .queryVector(Arrays.stream(queryVector)
                                            .toList())
                                    .k(3)
                                    .numCandidates(100)
                            ),
                    LawES.class
            );

            if (response == null || response.hits() == null || response.hits().hits() == null) {
                return null;
            }
            List<String> content = new ArrayList<>();
            for (Hit<LawES> hit : response.hits().hits()) {
                if (hit.source() != null && StringUtils.isNotBlank(hit.source().getContent())) {
                    content.add(hit.source().getContent());
                }
            }

            // 调用相关prompt
            String lawPrompt = String.format(PromptConstant.LAW_PROMPT, String.join(",", content));

            return llmSrv.chat(lawPrompt);

        } catch (Exception e) {
            logger.error("查询知识库失败", e);
        }

        return null;
    }

    @Override
    public void buildKnowledge(String path) {

        try {

            // 清空原来的知识库
            DeleteByQueryRequest deleteRequest = DeleteByQueryRequest.of(d -> d
                    .index(INDEX)
                    .query(q -> q.matchAll(MatchAllQuery.of(m -> m)))
            );
            try {
                esClient.deleteByQuery(deleteRequest);
            } catch (Exception e) {
                logger.error("清空知识库失败", e);
            }

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath*:" + path + "/**/*");

            for (Resource resource : resources) {
                String docContent;
                try (InputStream is = resource.getInputStream()) {
                    docContent = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }
                List<String> contents = ChunkUtils.slidingWindowChunk(docContent, 300, 50);
                if (CollectionUtils.isEmpty(contents)) {
                    continue;
                }
                // 调用
                for (String content : contents) {
                    List<EmbeddingDTO> embeddingDTOList = llmSrv.embedding(content);
                    if (CollectionUtils.isEmpty(embeddingDTOList)) {
                        continue;
                    }
                    Float[] vector = embeddingDTOList.get(0).getEmbedding();
                    LawES lawES = LawES.builder().content(content).vector(vector).build();
                    IndexRequest<LawES> indexRequest = IndexRequest.of(i -> i
                            .index(INDEX)
                            .document(lawES)
                    );
                    esClient.index(indexRequest);
                }
            }
        } catch (IOException e) {
            logger.error("查询资源文件失败", e);
        } catch (Exception e) {
            logger.error("构建知识库失败", e);
        }

    }
}
