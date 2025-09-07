package com.outscout.api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "forecasts")
public class Forecast {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal lat;
    
    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal lon;
    
    @Column(name = "hour_utc", nullable = false)
    private LocalDateTime hourUtc;
    
    @Column(name = "temp_c", precision = 5, scale = 2)
    private BigDecimal tempC;
    
    @Column(name = "precip_mm", precision = 5, scale = 2)
    private BigDecimal precipMm;
    
    @Column(name = "precip_prob", precision = 5, scale = 2)
    private BigDecimal precipProb;
    
    @Column(name = "wind_mps", precision = 5, scale = 2)
    private BigDecimal windMps;
    
    @Column(name = "uv_index", precision = 3, scale = 1)
    private BigDecimal uvIndex;
    
    @Column(nullable = false)
    private String source;
    
    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt = LocalDateTime.now();
    
    // Constructors
    public Forecast() {}
    
    public Forecast(BigDecimal lat, BigDecimal lon, LocalDateTime hourUtc, BigDecimal tempC,
                   BigDecimal precipMm, BigDecimal precipProb, BigDecimal windMps, 
                   BigDecimal uvIndex, String source) {
        this.lat = lat;
        this.lon = lon;
        this.hourUtc = hourUtc;
        this.tempC = tempC;
        this.precipMm = precipMm;
        this.precipProb = precipProb;
        this.windMps = windMps;
        this.uvIndex = uvIndex;
        this.source = source;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public BigDecimal getLat() { return lat; }
    public void setLat(BigDecimal lat) { this.lat = lat; }
    
    public BigDecimal getLon() { return lon; }
    public void setLon(BigDecimal lon) { this.lon = lon; }
    
    public LocalDateTime getHourUtc() { return hourUtc; }
    public void setHourUtc(LocalDateTime hourUtc) { this.hourUtc = hourUtc; }
    
    public BigDecimal getTempC() { return tempC; }
    public void setTempC(BigDecimal tempC) { this.tempC = tempC; }
    
    public BigDecimal getPrecipMm() { return precipMm; }
    public void setPrecipMm(BigDecimal precipMm) { this.precipMm = precipMm; }
    
    public BigDecimal getPrecipProb() { return precipProb; }
    public void setPrecipProb(BigDecimal precipProb) { this.precipProb = precipProb; }
    
    public BigDecimal getWindMps() { return windMps; }
    public void setWindMps(BigDecimal windMps) { this.windMps = windMps; }
    
    public BigDecimal getUvIndex() { return uvIndex; }
    public void setUvIndex(BigDecimal uvIndex) { this.uvIndex = uvIndex; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public LocalDateTime getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(LocalDateTime fetchedAt) { this.fetchedAt = fetchedAt; }
}
