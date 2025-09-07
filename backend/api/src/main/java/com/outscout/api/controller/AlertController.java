package com.outscout.api.controller;

import com.outscout.api.dto.AlertResponse;
import com.outscout.api.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {
    
    @Autowired
    private AlertService alertService;
    
    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAlerts(
            @RequestParam(required = false) Long parkId,
            @RequestParam(required = false) String severity,
            @RequestParam(defaultValue = "10") int limit) {
        
        List<AlertResponse> alerts = alertService.getAlerts(parkId, severity, limit);
        return ResponseEntity.ok(alerts);
    }
}
