package com.outscout.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SpotDetailsResponse {
    private Long id;
    private String name;
    private String description;
    private String parkName;
    private String region;
    private String authority;
    private String websiteUrl;
    private BigDecimal lat;
    private BigDecimal lon;
    private BigDecimal feeAud;
    private Boolean petAllowed;
    private Boolean bookable;
    private List<String> amenities;
    private LocalDateTime updatedAt;
    
    // Constructors
    public SpotDetailsResponse() {}
    
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
    
    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }
    
    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
    
    public BigDecimal getLat() { return lat; }
    public void setLat(BigDecimal lat) { this.lat = lat; }
    
    public BigDecimal getLon() { return lon; }
    public void setLon(BigDecimal lon) { this.lon = lon; }
    
    public BigDecimal getFeeAud() { return feeAud; }
    public void setFeeAud(BigDecimal feeAud) { this.feeAud = feeAud; }
    
    public Boolean getPetAllowed() { return petAllowed; }
    public void setPetAllowed(Boolean petAllowed) { this.petAllowed = petAllowed; }
    
    public Boolean getBookable() { return bookable; }
    public void setBookable(Boolean bookable) { this.bookable = bookable; }
    
    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
