package com.outscout.api.dto;

public class SpotSearchRequest {
    private String query;
    private String region;
    private Boolean petAllowed;
    private String amenities;
    private Double lat;
    private Double lon;
    private Double radius;
    private int page = 0;
    private int size = 20;
    
    // Constructors
    public SpotSearchRequest() {}
    
    // Getters and Setters
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public Boolean getPetAllowed() { return petAllowed; }
    public void setPetAllowed(Boolean petAllowed) { this.petAllowed = petAllowed; }
    
    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
    
    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }
    
    public Double getLon() { return lon; }
    public void setLon(Double lon) { this.lon = lon; }
    
    public Double getRadius() { return radius; }
    public void setRadius(Double radius) { this.radius = radius; }
    
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}
