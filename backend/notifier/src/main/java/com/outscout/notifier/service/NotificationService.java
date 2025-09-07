package com.outscout.notifier.service;

import com.outscout.notifier.dto.WeatherWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {
    
    private final ConcurrentHashMap<String, SseEmitter> activeStreams = new ConcurrentHashMap<>();
    
    @KafkaListener(topics = "plan_scored", groupId = "outscout-notifier")
    public void handleScoredPlan(String requestId, WeatherWindow window) {
        SseEmitter emitter = activeStreams.get(requestId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                    .name("plan_result")
                    .data(window));
            } catch (IOException e) {
                emitter.completeWithError(e);
                activeStreams.remove(requestId);
            }
        }
    }
    
    public void registerStream(String requestId, SseEmitter emitter) {
        activeStreams.put(requestId, emitter);
        
        emitter.onCompletion(() -> activeStreams.remove(requestId));
        emitter.onTimeout(() -> activeStreams.remove(requestId));
        emitter.onError((ex) -> activeStreams.remove(requestId));
    }
    
    public void completeStream(String requestId) {
        SseEmitter emitter = activeStreams.get(requestId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                    .name("plan_complete")
                    .data("{\"status\": \"completed\"}"));
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            } finally {
                activeStreams.remove(requestId);
            }
        }
    }
}
