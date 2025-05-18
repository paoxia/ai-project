package com.example.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ai.common.res.CommonResult;
import com.example.ai.common.utils.ParamCheckUtils;
import com.example.ai.infra.rag.RagSrv;
import com.example.ai.infra.rag.param.RagQueryParam;

/**
 * RAG
 */
@RestController
public class RagController {

    @Autowired
    private RagSrv ragSrv;

    /**
     * 大模型聊天
     *
     * @param param 聊天入参
     * @return 聊天返回
     */
    @PostMapping("/hunyuan/chat")
    public CommonResult<String> chat(@RequestBody RagQueryParam param) {
        // 参数校验
        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getQueryContent(), "查询问题为空");
        // 调用模型
        return CommonResult.success(ragSrv.query(param.getQueryContent()));
    }
}
