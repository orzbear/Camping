package com.outscout.api.dto;

import java.time.LocalDateTime;

public class AlertResponse {
    private Long id;
    private String parkName;
    private String severity;
    private String title;
    private String summary;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String source;
    private String url;
    private LocalDateTime fetchedAt;
    
    // Constructors
    public AlertResponse() {}
    
    public AlertResponse(Long id, String parkName, String severity, String title, String summary,
                        LocalDateTime startsAt, LocalDateTime endsAt, String source, String url,
                        LocalDateTime fetchedAt) {
        this.id = id;
        this.parkName = parkName;
        this.severity = severity;
        this.title = title;
        this.summary = summary;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.source = source;
        this.url = url;
        this.fetchedAt = fetchedAt;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getParkName() { return parkName; }
    public void setParkName(String parkName) { this.parkName = parkName; }
    
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public LocalDateTime getStartsAt() { return startsAt; }
    public void setStartsAt(LocalDateTime startsAt) { this.startsAt = startsAt; }
    
    public LocalDateTime getEndsAt() { return endsAt; }
    public void setEndsAt(LocalDateTime endsAt) { this.endsAt = endsAt; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public LocalDateTime getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(LocalDateTime fetchedAt) { this.fetchedAt = fetchedAt; }
}
