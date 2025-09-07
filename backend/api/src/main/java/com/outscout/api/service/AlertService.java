package com.outscout.api.service;

import com.outscout.api.dto.AlertResponse;
import com.outscout.api.entity.Alert;
import com.outscout.api.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {
    
    @Autowired
    private AlertRepository alertRepository;
    
    public List<AlertResponse> getAlerts(Long parkId, String severity, int limit) {
        List<Alert> alerts;
        
        if (parkId != null && severity != null) {
            alerts = alertRepository.findByParkIdAndSeverityOrderByFetchedAtDesc(parkId, severity);
        } else if (parkId != null) {
            alerts = alertRepository.findByParkIdOrderByFetchedAtDesc(parkId);
        } else if (severity != null) {
            alerts = alertRepository.findBySeverityOrderByFetchedAtDesc(severity);
        } else {
            alerts = alertRepository.findAllByOrderByFetchedAtDesc();
        }
        
        return alerts.stream()
            .limit(limit)
            .map(this::convertToAlertResponse)
            .collect(Collectors.toList());
    }
    
    private AlertResponse convertToAlertResponse(Alert alert) {
        AlertResponse response = new AlertResponse();
        response.setId(alert.getId());
        response.setParkName(alert.getPark() != null ? alert.getPark().getName() : null);
        response.setSeverity(alert.getSeverity());
        response.setTitle(alert.getTitle());
        response.setSummary(alert.getSummary());
        response.setStartsAt(alert.getStartsAt());
        response.setEndsAt(alert.getEndsAt());
        response.setSource(alert.getSource());
        response.setUrl(alert.getUrl());
        response.setFetchedAt(alert.getFetchedAt());
        return response;
    }
}
