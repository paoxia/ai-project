package com.example.ai.infra.mcp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址编码返回
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoCodeDTO {
    /**
     * 城市编码
     */
    @JsonProperty("citycode")
    private String cityCode;
    /**
     * 区域编码
     */
    @JsonProperty("adcode")
    private String adCode;
}
