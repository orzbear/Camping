package com.outscout.notifier.dto;

import java.time.LocalDateTime;

public class WeatherWindow {
    private String windowId;
    private Long spotId;
    private String spotName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double score;
    private String weatherSummary;
    private double temperature;
    private double precipitationChance;
    private double windSpeed;
    private double uvIndex;
    
    // Constructors
    public WeatherWindow() {}
    
    // Getters and Setters
    public String getWindowId() { return windowId; }
    public void setWindowId(String windowId) { this.windowId = windowId; }
    
    public Long getSpotId() { return spotId; }
    public void setSpotId(Long spotId) { this.spotId = spotId; }
    
    public String getSpotName() { return spotName; }
    public void setSpotName(String spotName) { this.spotName = spotName; }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    
    public String getWeatherSummary() { return weatherSummary; }
    public void setWeatherSummary(String weatherSummary) { this.weatherSummary = weatherSummary; }
    
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    
    public double getPrecipitationChance() { return precipitationChance; }
    public void setPrecipitationChance(double precipitationChance) { this.precipitationChance = precipitationChance; }
    
    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    
    public double getUvIndex() { return uvIndex; }
    public void setUvIndex(double uvIndex) { this.uvIndex = uvIndex; }
}
