package com.example.model.api.service;

import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.SSEResponseModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.hunyuan.v20230901.HunyuanClient;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatCompletionsRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.ChatCompletionsResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.Choice;
import com.tencentcloudapi.hunyuan.v20230901.models.Message;

/**
 * tencent hunyuan model
 */
@Service
public class HunyuanService {

    /**
     * chat接口
     *
     * @return 聊天回答
     */
    public String chat(String content) {
        try {
            Credential cred = new Credential(
                    System.getenv("TENCENTCLOUD_SECRET_ID"),
                    System.getenv("TENCENTCLOUD_SECRET_KEY")
            );

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.getHttpProfile().setReadTimeout(400); // 流式接口耗时可能较长
            HunyuanClient client = new HunyuanClient(cred, "ap-guangzhou", clientProfile);

            ChatCompletionsRequest req = new ChatCompletionsRequest();
            req.setModel("hunyuan-standard");
            Message msg = new Message();
            msg.setRole("user");
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
                        boolean iWantToCancelNow = false;
                        if (iWantToCancelNow) {
                            break;
                        }
                    }
                }
            } else {
                // 非 stream 示例
                // 通过 Stream=false 参数来指定非 stream 协议, 一次性拿到结果
                return client.ChatCompletions(req).getChoices()[0].getMessage().getContent();
            }
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 翻译
     *
     * @return
     */
    public String translate() {
        return "";
    }
}
