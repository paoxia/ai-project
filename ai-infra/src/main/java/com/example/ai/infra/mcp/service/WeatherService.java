package com.example.ai.infra.mcp.service;

import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.ai.infra.mcp.constant.StatusConstant;
import com.example.ai.infra.mcp.dto.GeoCodeDTO;
import com.example.ai.infra.mcp.dto.GeoCodeRespDTO;
import com.example.ai.infra.mcp.dto.WeatherDTO;
import com.example.ai.infra.mcp.dto.WeatherRespDTO;

@Service
public class WeatherService {

    /**
     * http调用模板
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 高德API KEY
     */
    private final static String AMAP_API_KEY = System.getenv("AMAP_API_KEY");

    /**
     * 高德地理API
     */
    private final static String AMAP_GEO_CODE_API = "https://restapi.amap.com/v3/geocode/geo?address=%s&key=%s";

    /**
     * 高德天气API
     */
    private final static String AMAP_WEATHER_API = "https://restapi.amap.com/v3/weather/weatherInfo?city=%s&key=%s";

    /**
     * 天气结果
     */
    private final static String WEATHER_RESULT = "今天%s的天气是%s";

    /**
     * 根据地址获取城市天气
     *
     * @param address 地址
     * @return 城市的天气
     */
    @Tool(description = "给定城市的天气预报")
    public String getCityWeatherForecast(String address) {
        // 查询地址对应的id
        String getGeoCodeUrl = String.format(AMAP_GEO_CODE_API, address, AMAP_API_KEY);
        GeoCodeRespDTO geoCodeRespDTO = restTemplate.getForObject(getGeoCodeUrl, GeoCodeRespDTO.class);
        if (Objects.isNull(geoCodeRespDTO) ||
                StringUtils.equals(StatusConstant.STATUS_FAIL, geoCodeRespDTO.getStatus())) {
            return StatusConstant.STATUS_FAIL_DESC;
        }

        if (CollectionUtils.isEmpty(geoCodeRespDTO.getGeoCodes())) {
            return StatusConstant.STATUS_FAIL_DESC;
        }

        GeoCodeDTO geoCodeDTO = geoCodeRespDTO.getGeoCodes().get(0);

        // 获取具体城市的编码
        String adCode = geoCodeDTO.getAdCode();
        // 获取天气
        String url = String.format(AMAP_WEATHER_API, adCode, AMAP_API_KEY);
        WeatherRespDTO weatherRespDTO = restTemplate.getForObject(url, WeatherRespDTO.class);
        if (Objects.isNull(weatherRespDTO) ||
                StringUtils.equals(StatusConstant.STATUS_FAIL, geoCodeRespDTO.getStatus())) {
            return StatusConstant.STATUS_FAIL_DESC;
        }

        if (CollectionUtils.isEmpty(weatherRespDTO.getLives())) {
            return StatusConstant.STATUS_FAIL_DESC;
        }

        WeatherDTO weatherDTO = weatherRespDTO.getLives().get(0);

        return String.format(WEATHER_RESULT, weatherDTO.getCity(), weatherDTO.getWeather());
    }


}