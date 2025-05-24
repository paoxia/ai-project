package com.example.ai.infra.mcp.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 天气获取
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRespDTO {
    /**
     * 返回结果状态值 0失败1成功
     */
    private String status;
    /**
     * 返回结果个数
     */
    private String count;
    /**
     * 天气情况
     */
    @JsonProperty("lives")
    private List<WeatherDTO> lives;

}
