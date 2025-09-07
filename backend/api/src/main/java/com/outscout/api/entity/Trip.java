package com.outscout.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campsite_id", nullable = false)
    private Campsite campsite;
    
    @Column(name = "start_dt", nullable = false)
    private LocalDateTime startDt;
    
    @Column(name = "end_dt", nullable = false)
    private LocalDateTime endDt;
    
    @Column(nullable = false)
    private Integer pax;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors
    public Trip() {}
    
    public Trip(String userId, Campsite campsite, LocalDateTime startDt, LocalDateTime endDt, 
               Integer pax, String notes) {
        this.userId = userId;
        this.campsite = campsite;
        this.startDt = startDt;
        this.endDt = endDt;
        this.pax = pax;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public Campsite getCampsite() { return campsite; }
    public void setCampsite(Campsite campsite) { this.campsite = campsite; }
    
    public LocalDateTime getStartDt() { return startDt; }
    public void setStartDt(LocalDateTime startDt) { this.startDt = startDt; }
    
    public LocalDateTime getEndDt() { return endDt; }
    public void setEndDt(LocalDateTime endDt) { this.endDt = endDt; }
    
    public Integer getPax() { return pax; }
    public void setPax(Integer pax) { this.pax = pax; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
