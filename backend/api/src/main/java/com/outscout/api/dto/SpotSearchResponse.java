package com.outscout.api.dto;

import java.util.List;

public class SpotSearchResponse {
    private List<SpotSummary> spots;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private int size;
    
    // Constructors
    public SpotSearchResponse() {}
    
    public SpotSearchResponse(List<SpotSummary> spots, int totalElements, int totalPages, int currentPage, int size) {
        this.spots = spots;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.size = size;
    }
    
    // Getters and Setters
    public List<SpotSummary> getSpots() { return spots; }
    public void setSpots(List<SpotSummary> spots) { this.spots = spots; }
    
    public int getTotalElements() { return totalElements; }
    public void setTotalElements(int totalElements) { this.totalElements = totalElements; }
    
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}
