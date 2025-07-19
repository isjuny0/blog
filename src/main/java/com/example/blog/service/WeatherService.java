package com.example.blog.service;

import com.example.blog.dto.response.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherResponseDto getCurrentWeather(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("q", city)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric") // 섭씨 온도
                .build().toUriString();

        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        String weather = json.getJSONArray("weather").getJSONObject(0).getString("main");
        double temp = json.getJSONObject("main").getDouble("temp");

        return new WeatherResponseDto(weather, temp);
    }
}
