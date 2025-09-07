package com.outscout.planner.service;

import com.outscout.planner.dto.PlanRequest;
import com.outscout.planner.dto.WeatherWindow;
import com.outscout.planner.entity.Forecast;
import com.outscout.planner.repository.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WeatherScoringService {
    
    @Autowired
    private ForecastRepository forecastRepository;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @KafkaListener(topics = "plan_requested", groupId = "outscout-planner")
    public void processPlanRequest(String requestId, PlanRequest request) {
        try {
            List<WeatherWindow> windows = scoreWeatherWindows(request);
            publishScoredWindows(requestId, windows);
        } catch (Exception e) {
            publishError(requestId, e.getMessage());
        }
    }
    
    private List<WeatherWindow> scoreWeatherWindows(PlanRequest request) {
        List<WeatherWindow> windows = new ArrayList<>();
        
        // For demo purposes, create dummy weather windows
        LocalDateTime startDate = request.getStartDate();
        LocalDateTime endDate = request.getEndDate();
        
        // Create 3 dummy windows with different scores
        for (int i = 0; i < 3; i++) {
            WeatherWindow window = new WeatherWindow();
            window.setWindowId(UUID.randomUUID().toString());
            window.setSpotId(1L + i); // Demo spot IDs
            window.setSpotName("Demo Campsite " + (i + 1));
            window.setStartTime(startDate.plusDays(i));
            window.setEndTime(endDate.plusDays(i));
            window.setScore(0.7 + (i * 0.1)); // Scores: 0.7, 0.8, 0.9
            window.setWeatherSummary("Sunny with light winds");
            window.setTemperature(22.0 + i);
            window.setPrecipitationChance(10.0 - (i * 3));
            window.setWindSpeed(8.0 + i);
            window.setUvIndex(6.0 + i);
            
            windows.add(window);
        }
        
        return windows;
    }
    
    private void publishScoredWindows(String requestId, List<WeatherWindow> windows) {
        for (WeatherWindow window : windows) {
            kafkaTemplate.send("plan_scored", requestId, window);
        }
    }
    
    private void publishError(String requestId, String errorMessage) {
        WeatherWindow errorWindow = new WeatherWindow();
        errorWindow.setWindowId(requestId);
        errorWindow.setScore(0.0);
        errorWindow.setWeatherSummary("Error: " + errorMessage);
        
        kafkaTemplate.send("plan_scored", requestId, errorWindow);
    }
    
    // Real weather scoring logic (simplified)
    private double calculateWeatherScore(List<Forecast> forecasts) {
        if (forecasts.isEmpty()) return 0.0;
        
        double totalScore = 0.0;
        int count = 0;
        
        for (Forecast forecast : forecasts) {
            double score = 1.0;
            
            // Temperature scoring (prefer 15-25°C)
            double temp = forecast.getTempC().doubleValue();
            if (temp < 10 || temp > 30) {
                score *= 0.3;
            } else if (temp >= 15 && temp <= 25) {
                score *= 1.0;
            } else {
                score *= 0.7;
            }
            
            // Precipitation scoring (prefer low precipitation)
            double precipProb = forecast.getPrecipProb().doubleValue();
            if (precipProb > 70) {
                score *= 0.2;
            } else if (precipProb > 40) {
                score *= 0.6;
            } else {
                score *= 1.0;
            }
            
            // Wind scoring (prefer moderate winds)
            double windSpeed = forecast.getWindMps().doubleValue();
            if (windSpeed > 15) {
                score *= 0.4;
            } else if (windSpeed > 10) {
                score *= 0.8;
            } else {
                score *= 1.0;
            }
            
            totalScore += score;
            count++;
        }
        
        return count > 0 ? totalScore / count : 0.0;
    }
}
