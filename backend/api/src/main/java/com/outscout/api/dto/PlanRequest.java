package com.outscout.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PlanRequest {
    private String userId;
    private List<Long> preferredSpots;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer pax;
    private List<String> requiredAmenities;
    private Boolean petFriendly;
    private String region;
    private Double maxDistance;
    private String notes;
    
    // Constructors
    public PlanRequest() {}
    
    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public List<Long> getPreferredSpots() { return preferredSpots; }
    public void setPreferredSpots(List<Long> preferredSpots) { this.preferredSpots = preferredSpots; }
    
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    
    public Integer getPax() { return pax; }
    public void setPax(Integer pax) { this.pax = pax; }
    
    public List<String> getRequiredAmenities() { return requiredAmenities; }
    public void setRequiredAmenities(List<String> requiredAmenities) { this.requiredAmenities = requiredAmenities; }
    
    public Boolean getPetFriendly() { return petFriendly; }
    public void setPetFriendly(Boolean petFriendly) { this.petFriendly = petFriendly; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public Double getMaxDistance() { return maxDistance; }
    public void setMaxDistance(Double maxDistance) { this.maxDistance = maxDistance; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
