package com.outscout.ingest.service;

import com.outscout.ingest.dto.OpenMeteoResponse;
import com.outscout.ingest.entity.Forecast;
import com.outscout.ingest.repository.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherIngestService {
    
    @Autowired
    private ForecastRepository forecastRepository;
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${open-meteo.base-url}")
    private String openMeteoBaseUrl;
    
    // Demo coordinates for major Australian cities
    private static final List<Location> DEMO_LOCATIONS = List.of(
        new Location(-33.8688, 151.2093, "Sydney"),
        new Location(-37.8136, 144.9631, "Melbourne"),
        new Location(-27.4698, 153.0251, "Brisbane"),
        new Location(-34.9285, 138.6007, "Adelaide"),
        new Location(-31.9505, 115.8605, "Perth"),
        new Location(-35.2809, 149.1300, "Canberra"),
        new Location(-42.8821, 147.3272, "Hobart"),
        new Location(-12.4634, 130.8456, "Darwin")
    );
    
    @Scheduled(fixedRate = 3600000) // Every hour
    public void ingestWeatherData() {
        for (Location location : DEMO_LOCATIONS) {
            try {
                fetchAndStoreWeatherData(location);
            } catch (Exception e) {
                // Log error and continue with other locations
                System.err.println("Failed to fetch weather for " + location.name + ": " + e.getMessage());
            }
        }
    }
    
    private void fetchAndStoreWeatherData(Location location) {
        String url = String.format("%s?latitude=%.4f&longitude=%.4f&hourly=temperature_2m,precipitation,precipitation_probability,wind_speed_10m,uv_index&timezone=Australia/Sydney&forecast_days=3",
            openMeteoBaseUrl, location.lat, location.lon);
        
        try {
            OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);
            if (response != null && response.getHourly() != null) {
                storeForecastData(location, response);
                publishForecastRefreshed(location);
            }
        } catch (Exception e) {
            // For demo purposes, create dummy data if API fails
            createDummyForecastData(location);
        }
    }
    
    private void storeForecastData(Location location, OpenMeteoResponse response) {
        List<String> times = response.getHourly().getTime();
        List<Double> temperatures = response.getHourly().getTemperature2m();
        List<Double> precipitation = response.getHourly().getPrecipitation();
        List<Double> precipitationProb = response.getHourly().getPrecipitationProbability();
        List<Double> windSpeed = response.getHourly().getWindSpeed10m();
        List<Double> uvIndex = response.getHourly().getUvIndex();
        
        List<Forecast> forecasts = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        
        for (int i = 0; i < times.size(); i++) {
            Forecast forecast = new Forecast();
            forecast.setLat(BigDecimal.valueOf(location.lat));
            forecast.setLon(BigDecimal.valueOf(location.lon));
            forecast.setHourUtc(LocalDateTime.parse(times.get(i), formatter));
            forecast.setTempC(BigDecimal.valueOf(temperatures.get(i)));
            forecast.setPrecipMm(BigDecimal.valueOf(precipitation.get(i)));
            forecast.setPrecipProb(BigDecimal.valueOf(precipitationProb.get(i)));
            forecast.setWindMps(BigDecimal.valueOf(windSpeed.get(i)));
            forecast.setUvIndex(BigDecimal.valueOf(uvIndex.get(i)));
            forecast.setSource("open-meteo");
            
            forecasts.add(forecast);
        }
        
        forecastRepository.saveAll(forecasts);
    }
    
    private void createDummyForecastData(Location location) {
        List<Forecast> forecasts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        
        for (int i = 0; i < 72; i++) { // 3 days of hourly data
            Forecast forecast = new Forecast();
            forecast.setLat(BigDecimal.valueOf(location.lat));
            forecast.setLon(BigDecimal.valueOf(location.lon));
            forecast.setHourUtc(now.plusHours(i));
            forecast.setTempC(BigDecimal.valueOf(20 + Math.sin(i * 0.1) * 10));
            forecast.setPrecipMm(BigDecimal.valueOf(Math.random() * 5));
            forecast.setPrecipProb(BigDecimal.valueOf(Math.random() * 100));
            forecast.setWindMps(BigDecimal.valueOf(5 + Math.random() * 15));
            forecast.setUvIndex(BigDecimal.valueOf(1 + Math.random() * 10));
            forecast.setSource("dummy");
            
            forecasts.add(forecast);
        }
        
        forecastRepository.saveAll(forecasts);
    }
    
    private void publishForecastRefreshed(Location location) {
        kafkaTemplate.send("forecast_refreshed", location.name, 
            String.format("Weather data refreshed for %s (%.4f, %.4f)", 
                location.name, location.lat, location.lon));
    }
    
    private static class Location {
        final double lat;
        final double lon;
        final String name;
        
        Location(double lat, double lon, String name) {
            this.lat = lat;
            this.lon = lon;
            this.name = name;
        }
    }
}
