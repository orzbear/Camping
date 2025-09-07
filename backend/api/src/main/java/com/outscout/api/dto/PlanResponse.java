package com.outscout.api.dto;

public class PlanResponse {
    private String requestId;
    private String status;
    private String message;
    
    // Constructors
    public PlanResponse() {}
    
    public PlanResponse(String requestId, String status, String message) {
        this.requestId = requestId;
        this.status = status;
        this.message = message;
    }
    
    // Getters and Setters
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
