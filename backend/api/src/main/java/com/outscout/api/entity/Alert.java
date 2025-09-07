package com.outscout.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "park_id")
    private Park park;
    
    @Column(nullable = false)
    private String severity;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    @Column(name = "starts_at")
    private LocalDateTime startsAt;
    
    @Column(name = "ends_at")
    private LocalDateTime endsAt;
    
    @Column(nullable = false)
    private String source;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt = LocalDateTime.now();
    
    // Constructors
    public Alert() {}
    
    public Alert(Park park, String severity, String title, String summary, 
                LocalDateTime startsAt, LocalDateTime endsAt, String source, String url) {
        this.park = park;
        this.severity = severity;
        this.title = title;
        this.summary = summary;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.source = source;
        this.url = url;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Park getPark() { return park; }
    public void setPark(Park park) { this.park = park; }
    
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
