package com.example.ai.model.api.service.impl;

import org.springframework.stereotype.Service;
import com.example.ai.common.exception.ApiException;
import com.example.ai.model.api.service.HunYuanSrv;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.SSEResponseModel;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.hunyuan.v20230901.HunyuanClient;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatCompletionsRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatCompletionsResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatTranslationsRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatTranslationsResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.Choice;
import com.tencentcloudapi.hunyuan.v20230901.models.Message;

/**
 * tencent hunyuan model
 */
@Service
public class HunYuanSrvImpl implements HunYuanSrv {
    /**
     * region
     */
    public final static String REGION = "ap-guangzhou";
    /**
     * 聊天算法模型
     */
    public final static String CHAT_MODEL = "hunyuan-standard";
    /**
     * 翻译算法模型
     */
    public final static String TRANSLATE_MODEL = "hunyuan-translation";
    /**
     * 角色
     */
    public final static String ROLE = "user";
    /**
     * 腾讯云账号secret id,可以在windows/linux/mac系统变量中设置
     */
    public final static String ENV_SECRET_ID = "TENCENTCLOUD_SECRET_ID";
    /**
     * 腾讯云账号secret key,可以在windows/linux/mac系统变量中设置
     */
    public final static String ENV_SECRET_KEY = "TENCENTCLOUD_SECRET_KEY";

    /**
     * chat
     *
     * @param content 输入内容
     * @return 聊天回答
     */
    @Override
    public String chat(String content) {
        try {
            Credential cred = new Credential(
                    System.getenv(ENV_SECRET_ID),
                    System.getenv(ENV_SECRET_KEY)
            );
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.getHttpProfile().setReadTimeout(400); // 流式接口耗时可能较长
            HunyuanClient client = new HunyuanClient(cred, REGION, clientProfile);

            ChatCompletionsRequest req = new ChatCompletionsRequest();
            req.setModel(CHAT_MODEL);
            Message msg = new Message();
            msg.setRole(ROLE);
            msg.setContent(content);
            req.setMessages(new Message[]{msg});
            // hunyuan ChatCompletions 同时支持 stream 和非 stream 的情况
            req.setStream(false);
            if (req.getStream()) {
                try (ChatCompletionsResponse resp = client.ChatCompletions(req)) {
                    // stream 示例
                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    for (SSEResponseModel.SSE e : resp) {
                        ChatCompletionsResponse eventModel = gson.fromJson(e.Data, ChatCompletionsResponse.class);
                        Choice[] choices = eventModel.getChoices();
                        if (choices.length > 0) {
                            return choices[0].getDelta().getContent();
                        }
                        // 如果希望在任意时刻中止事件流, 使用 break 即可
                        // boolean iWantToCancelNow = false;
                        // if (iWantToCancelNow) {
                        //     break;
                        // }
                    }
                }
            } else {
                // 非 stream 示例
                // 通过 Stream=false 参数来指定非 stream 协议, 一次性拿到结果
                return client.ChatCompletions(req).getChoices()[0].getMessage().getContent();
            }
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return null;
    }

    @Override
    public String translate(String content) {
        try {
            Credential cred = new Credential(
                    System.getenv(ENV_SECRET_ID),
                    System.getenv(ENV_SECRET_KEY)
            );

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.getHttpProfile().setReadTimeout(400); // 流式接口耗时可能较长
            HunyuanClient client = new HunyuanClient(cred, REGION, clientProfile);

            ChatTranslationsRequest req = new ChatTranslationsRequest();
            req.setModel(TRANSLATE_MODEL);
            req.setText(content);
            // hunyuan ChatCompletions 同时支持 stream 和非 stream 的情况
            req.setStream(false);
            req.setTarget("zh");
            if (req.getStream()) {
                try (ChatTranslationsResponse resp = client.ChatTranslations(req)) {
                    // stream 示例
                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    for (SSEResponseModel.SSE e : resp) {
                        ChatCompletionsResponse eventModel = gson.fromJson(e.Data, ChatCompletionsResponse.class);
                        Choice[] choices = eventModel.getChoices();
                        if (choices.length > 0) {
                            return choices[0].getDelta().getContent();
                        }

                        // 如果希望在任意时刻中止事件流, 使用 break 即可
                        // boolean iWantToCancelNow = false;
                        // if (iWantToCancelNow) {
                        //     break;
                        // }
                    }
                }
            } else {
                // 非 stream 示例
                // 通过 Stream=false 参数来指定非 stream 协议, 一次性拿到结果
                return client.ChatTranslations(req).getChoices()[0].getMessage().getContent();
            }
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return null;
    }

    @Override
    public String embedding(String content) {
        return "";
    }
}