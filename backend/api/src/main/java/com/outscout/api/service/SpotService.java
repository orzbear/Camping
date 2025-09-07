package com.outscout.api.service;

import com.outscout.api.dto.SpotSearchRequest;
import com.outscout.api.dto.SpotSearchResponse;
import com.outscout.api.dto.SpotDetailsResponse;
import com.outscout.api.dto.SpotSummary;
import com.outscout.api.entity.Campsite;
import com.outscout.api.repository.CampsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotService {
    
    @Autowired
    private CampsiteRepository campsiteRepository;
    
    public SpotSearchResponse searchSpots(SpotSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Campsite> campsites = campsiteRepository.findAll(pageable);
        
        List<SpotSummary> spotSummaries = campsites.getContent().stream()
            .map(this::convertToSpotSummary)
            .collect(Collectors.toList());
        
        return new SpotSearchResponse(
            spotSummaries,
            (int) campsites.getTotalElements(),
            campsites.getTotalPages(),
            campsites.getNumber(),
            campsites.getSize()
        );
    }
    
    public SpotDetailsResponse getSpotDetails(Long id) {
        Campsite campsite = campsiteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Campsite not found"));
        
        return convertToSpotDetails(campsite);
    }
    
    private SpotSummary convertToSpotSummary(Campsite campsite) {
        SpotSummary summary = new SpotSummary();
        summary.setId(campsite.getId());
        summary.setName(campsite.getName());
        summary.setDescription(campsite.getDescription());
        summary.setParkName(campsite.getPark().getName());
        summary.setRegion(campsite.getPark().getRegion());
        summary.setLat(campsite.getLat());
        summary.setLon(campsite.getLon());
        summary.setFeeAud(campsite.getFeeAud());
        summary.setPetAllowed(campsite.getPetAllowed());
        
        // Convert amenities
        if (campsite.getAmenities() != null) {
            List<String> amenities = List.of();
            // TODO: Convert amenities to list
            summary.setAmenities(amenities);
        }
        
        // Set fee bucket
        if (campsite.getFeeAud() != null) {
            if (campsite.getFeeAud().doubleValue() == 0) {
                summary.setFeeBucket("FREE");
            } else if (campsite.getFeeAud().doubleValue() < 20) {
                summary.setFeeBucket("LOW");
            } else if (campsite.getFeeAud().doubleValue() < 50) {
                summary.setFeeBucket("MEDIUM");
            } else {
                summary.setFeeBucket("HIGH");
            }
        } else {
            summary.setFeeBucket("UNKNOWN");
        }
        
        return summary;
    }
    
    private SpotDetailsResponse convertToSpotDetails(Campsite campsite) {
        SpotDetailsResponse details = new SpotDetailsResponse();
        details.setId(campsite.getId());
        details.setName(campsite.getName());
        details.setDescription(campsite.getDescription());
        details.setParkName(campsite.getPark().getName());
        details.setRegion(campsite.getPark().getRegion());
        details.setAuthority(campsite.getPark().getAuthority());
        details.setWebsiteUrl(campsite.getPark().getWebsiteUrl());
        details.setLat(campsite.getLat());
        details.setLon(campsite.getLon());
        details.setFeeAud(campsite.getFeeAud());
        details.setPetAllowed(campsite.getPetAllowed());
        details.setBookable(campsite.getBookable());
        details.setUpdatedAt(campsite.getUpdatedAt());
        
        // Convert amenities
        if (campsite.getAmenities() != null) {
            List<String> amenities = List.of();
            // TODO: Convert amenities to list
            details.setAmenities(amenities);
        }
        
        return details;
    }
}
