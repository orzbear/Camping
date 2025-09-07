package com.outscout.api.service;

import com.outscout.api.dto.PlanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class PlanService {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    private final ConcurrentHashMap<String, SseEmitter> activeStreams = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    
    public void processPlanRequest(String requestId, PlanRequest request) {
        // Publish to Kafka for processing
        kafkaTemplate.send("plan_requested", requestId, request);
        
        // Schedule dummy results for demo
        scheduler.schedule(() -> {
            sendDummyResults(requestId);
        }, 2, TimeUnit.SECONDS);
    }
    
    public SseEmitter createPlanStream(String requestId) {
        SseEmitter emitter = new SseEmitter(30000L); // 30 second timeout
        activeStreams.put(requestId, emitter);
        
        emitter.onCompletion(() -> activeStreams.remove(requestId));
        emitter.onTimeout(() -> activeStreams.remove(requestId));
        emitter.onError((ex) -> activeStreams.remove(requestId));
        
        return emitter;
    }
    
    private void sendDummyResults(String requestId) {
        SseEmitter emitter = activeStreams.get(requestId);
        if (emitter != null) {
            try {
                // Send dummy weather windows
                for (int i = 1; i <= 3; i++) {
                    String data = String.format("{\"window\": %d, \"score\": %.2f, \"spot\": \"Demo Spot %d\", \"date\": \"2024-01-%02d\"}", 
                        i, 0.8 + (i * 0.05), i, 15 + i);
                    emitter.send(SseEmitter.event().name("plan_result").data(data));
                    Thread.sleep(1000);
                }
                emitter.send(SseEmitter.event().name("plan_complete").data("{\"status\": \"completed\"}"));
                emitter.complete();
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            } finally {
                activeStreams.remove(requestId);
            }
        }
    }
}
