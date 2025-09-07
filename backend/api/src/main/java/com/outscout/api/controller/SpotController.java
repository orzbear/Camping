package com.outscout.api.controller;

import com.outscout.api.dto.SpotSearchRequest;
import com.outscout.api.dto.SpotSearchResponse;
import com.outscout.api.dto.SpotDetailsResponse;
import com.outscout.api.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spots")
@CrossOrigin(origins = "*")
public class SpotController {
    
    @Autowired
    private SpotService spotService;
    
    @GetMapping("/search")
    public ResponseEntity<SpotSearchResponse> searchSpots(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Boolean petAllowed,
            @RequestParam(required = false) String amenities,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) Double radius,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        SpotSearchRequest request = new SpotSearchRequest();
        request.setQuery(query);
        request.setRegion(region);
        request.setPetAllowed(petAllowed);
        request.setAmenities(amenities);
        request.setLat(lat);
        request.setLon(lon);
        request.setRadius(radius);
        request.setPage(page);
        request.setSize(size);
        
        SpotSearchResponse response = spotService.searchSpots(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SpotDetailsResponse> getSpotDetails(@PathVariable Long id) {
        SpotDetailsResponse response = spotService.getSpotDetails(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}/forecast")
    public ResponseEntity<?> getSpotForecast(@PathVariable Long id) {
        // TODO: Implement forecast retrieval
        return ResponseEntity.ok().body("Forecast data for spot " + id);
    }
}
