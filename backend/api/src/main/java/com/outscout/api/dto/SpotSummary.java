package com.outscout.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class SpotSummary {
    private Long id;
    private String name;
    private String description;
    private String parkName;
    private String region;
    private BigDecimal lat;
    private BigDecimal lon;
    private BigDecimal feeAud;
    private Boolean petAllowed;
    private List<String> amenities;
    private String feeBucket;
    
    // Constructors
    public SpotSummary() {}
    
    public SpotSummary(Long id, String name, String description, String parkName, String region,
                      BigDecimal lat, BigDecimal lon, BigDecimal feeAud, Boolean petAllowed,
                      List<String> amenities, String feeBucket) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parkName = parkName;
        this.region = region;
        this.lat = lat;
        this.lon = lon;
        this.feeAud = feeAud;
        this.petAllowed = petAllowed;
        this.amenities = amenities;
        this.feeBucket = feeBucket;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getParkName() { return parkName; }
    public void setParkName(String parkName) { this.parkName = parkName; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public BigDecimal getLat() { return lat; }
    public void setLat(BigDecimal lat) { this.lat = lat; }
    
    public BigDecimal getLon() { return lon; }
    public void setLon(BigDecimal lon) { this.lon = lon; }
    
    public BigDecimal getFeeAud() { return feeAud; }
    public void setFeeAud(BigDecimal feeAud) { this.feeAud = feeAud; }
    
    public Boolean getPetAllowed() { return petAllowed; }
    public void setPetAllowed(Boolean petAllowed) { this.petAllowed = petAllowed; }
    
    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
    
    public String getFeeBucket() { return feeBucket; }
    public void setFeeBucket(String feeBucket) { this.feeBucket = feeBucket; }
}
