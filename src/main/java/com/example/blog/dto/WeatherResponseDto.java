package com.example.blog.dto;

import lombok.Getter;

@Getter
public class WeatherResponseDto {

    private String weather;
    private double temperature;

    public WeatherResponseDto(String weather, double temperature) {
        this.weather = weather;
        this.temperature = temperature;
    }
}
