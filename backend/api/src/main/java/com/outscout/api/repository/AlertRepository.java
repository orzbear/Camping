package com.outscout.api.repository;

import com.outscout.api.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    
    List<Alert> findByParkIdOrderByFetchedAtDesc(Long parkId);
    
    List<Alert> findBySeverityOrderByFetchedAtDesc(String severity);
    
    List<Alert> findByParkIdAndSeverityOrderByFetchedAtDesc(Long parkId, String severity);
    
    List<Alert> findAllByOrderByFetchedAtDesc();
}
