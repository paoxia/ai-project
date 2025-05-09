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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/llm")
@Tag(name = "llm")
public class AiController {

    @Autowired
    private HunYuanSrv hunyuanSrv;

    /**
     * 大模型聊天
     *
     * @param param 聊天入参
     * @return 聊天返回
     */
    @PostMapping("/opt/chat")
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
    @PostMapping("/opt/translate")
    public CommonResult<String> translate(@RequestBody ChatParam param) {
        // 参数校验
        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getContent(), "入参为空");
        // 调用模型
        // todo 翻译service实现
        return CommonResult.success(hunyuanSrv.chat(param.getContent()));
    }
}
