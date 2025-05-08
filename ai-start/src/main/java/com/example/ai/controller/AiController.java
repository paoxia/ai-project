package com.example.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ai.common.res.CommonResult;
import com.example.ai.common.utils.ParamCheckUtils;
import com.example.ai.model.api.service.HunyuanService;
import com.example.ai.param.ChatParam;

@RestController
public class AiController {

    @Autowired
    private HunyuanService hunyuanSrv;

    public CommonResult<String> chat(@RequestBody ChatParam param) {

        ParamCheckUtils.checkObjNotNull(param, "参数为空");
        ParamCheckUtils.checkStrNotBlank(param.getContent(), "入参为空");

        return CommonResult.success(hunyuanSrv.chat(param.getContent()));
    }
}
