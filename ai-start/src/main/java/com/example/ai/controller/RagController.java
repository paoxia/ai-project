package com.example.ai.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ai.common.res.CommonResult;
import com.example.ai.common.utils.ParamCheckUtils;
import com.example.ai.infra.rag.RagSrv;
import com.example.ai.infra.rag.param.RagQueryParam;

/**
 * RAG
 */
@RestController
@RequestMapping("/rag")
public class RagController {

    @Autowired
    @Qualifier("lawRagSrv")
    private RagSrv ragSrv;

    /**
     * 法律书籍路径
     */
    private final static String LAW_PATH = "lawbook";

    /**
     * 法律大模型
     *
     * @param param 聊天入参
     * @return 聊天返回
     */
    @PostMapping("/law/chat")
    public CommonResult<String> ragChat(@RequestBody RagQueryParam param) {
        // 参数校验
        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getQueryContent(), "查询问题为空");
        // 调用模型
        return CommonResult.success(ragSrv.chat(param.getQueryContent()));
    }

    /**
     * 法律大模型
     *
     * @param param 聊天入参
     * @return 聊天返回
     */
    @PostMapping("/law/query")
    public CommonResult<List<String>> ragQuery(@RequestBody RagQueryParam param) {
        // 参数校验
        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getQueryContent(), "查询问题为空");
        // 调用模型
        return CommonResult.success(ragSrv.query(param.getQueryContent()));
    }

    /**
     * 构建法律知识库
     *
     * @return 是否成功
     */
    @PostMapping("/law/buildKnowLedge")
    public CommonResult buildKnowLedge() {
        // 调用构建知识库
        ragSrv.buildKnowledge(LAW_PATH);

        return CommonResult.success();
    }
}
