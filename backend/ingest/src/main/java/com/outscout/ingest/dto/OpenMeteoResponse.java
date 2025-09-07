package com.outscout.ingest.dto;

import java.util.List;

public class OpenMeteoResponse {
    private HourlyData hourly;
    
    // Getters and Setters
    public HourlyData getHourly() { return hourly; }
    public void setHourly(HourlyData hourly) { this.hourly = hourly; }
    
    public static class HourlyData {
        private List<String> time;
        private List<Double> temperature_2m;
        private List<Double> precipitation;
        private List<Double> precipitation_probability;
        private List<Double> wind_speed_10m;
        private List<Double> uv_index;
        
        // Getters and Setters
        public List<String> getTime() { return time; }
        public void setTime(List<String> time) { this.time = time; }
        
        public List<Double> getTemperature2m() { return temperature_2m; }
        public void setTemperature_2m(List<Double> temperature_2m) { this.temperature_2m = temperature_2m; }
        
        public List<Double> getPrecipitation() { return precipitation; }
        public void setPrecipitation(List<Double> precipitation) { this.precipitation = precipitation; }
        
        public List<Double> getPrecipitationProbability() { return precipitation_probability; }
        public void setPrecipitation_probability(List<Double> precipitation_probability) { this.precipitation_probability = precipitation_probability; }
        
        public List<Double> getWindSpeed10m() { return wind_speed_10m; }
        public void setWind_speed_10m(List<Double> wind_speed_10m) { this.wind_speed_10m = wind_speed_10m; }
        
        public List<Double> getUvIndex() { return uv_index; }
        public void setUv_index(List<Double> uv_index) { this.uv_index = uv_index; }
    }
}
