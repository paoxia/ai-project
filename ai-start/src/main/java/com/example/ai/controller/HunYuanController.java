package com.example.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ai.common.res.CommonResult;
import com.example.ai.common.utils.ParamCheckUtils;
import com.example.ai.model.api.service.HunYuanSrv;
import com.example.ai.param.ChatParam;
import com.example.ai.param.EmbeddingParam;
import com.example.ai.param.TranslateParam;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/llm")
@Tag(name = "llm")
public class HunYuanController {

    @Autowired
    private HunYuanSrv hunyuanSrv;

    /**
     * 大模型聊天
     *
     * @param param 聊天入参
     * @return 聊天返回
     */
    @PostMapping("/hunyuan/chat")
    public CommonResult<String> chat(@RequestBody ChatParam param) {
        // 参数校验
        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getContent(), "入参为空");
        // 调用模型
        return CommonResult.success(hunyuanSrv.chat(param.getContent()));
    }

    /**
     * 大模型聊天
     *
     * @param param 聊天入参
     * @return 聊天返回
     */
    @PostMapping("/hunyuan/translate")
    public CommonResult<String> translate(@RequestBody TranslateParam param) {
        // 参数校验
        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getContent(), "入参为空");
        // 调用模型
        return CommonResult.success(hunyuanSrv.translate(param.getContent()));
    }

    /**
     * 向量化
     *
     * @param param 聊天入参
     * @return 聊天返回
     */
    @PostMapping("/hunyuan/embedding")
    public CommonResult<String> embedding(@RequestBody EmbeddingParam param) {
        // 参数校验
        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getContent(), "入参为空");
        // 调用模型
        return CommonResult.success(hunyuanSrv.embedding(param.getContent()));
    }
}