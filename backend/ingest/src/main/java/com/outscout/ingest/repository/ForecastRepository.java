package com.outscout.ingest.repository;

import com.outscout.ingest.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    
    @Query("SELECT f FROM Forecast f WHERE f.lat = :lat AND f.lon = :lon AND f.hourUtc >= :startTime ORDER BY f.hourUtc")
    List<Forecast> findByLocationAndTimeRange(@Param("lat") BigDecimal lat, 
                                            @Param("lon") BigDecimal lon, 
                                            @Param("startTime") LocalDateTime startTime);
    
    @Query("SELECT f FROM Forecast f WHERE f.lat = :lat AND f.lon = :lon AND f.hourUtc BETWEEN :startTime AND :endTime ORDER BY f.hourUtc")
    List<Forecast> findByLocationAndTimeWindow(@Param("lat") BigDecimal lat, 
                                             @Param("lon") BigDecimal lon, 
                                             @Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime);
    
    void deleteByFetchedAtBefore(LocalDateTime cutoffTime);
}
