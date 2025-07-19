package com.example.blog.controller;

import com.example.blog.dto.response.WeatherResponseDto;
import com.example.blog.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public WeatherResponseDto getWeather(@RequestParam String city) {
        return weatherService.getCurrentWeather(city);
    }
}
