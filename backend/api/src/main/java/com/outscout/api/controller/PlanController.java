package com.outscout.api.controller;

import com.outscout.api.dto.PlanRequest;
import com.outscout.api.dto.PlanResponse;
import com.outscout.api.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@RequestMapping("/api/plan")
@CrossOrigin(origins = "*")
public class PlanController {
    
    @Autowired
    private PlanService planService;
    
    @PostMapping
    public ResponseEntity<PlanResponse> createPlan(@RequestBody PlanRequest request) {
        String requestId = UUID.randomUUID().toString();
        planService.processPlanRequest(requestId, request);
        
        PlanResponse response = new PlanResponse();
        response.setRequestId(requestId);
        response.setStatus("PROCESSING");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value = "/stream/{requestId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamPlanResults(@PathVariable String requestId) {
        return planService.createPlanStream(requestId);
    }
}
