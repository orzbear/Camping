package com.outscout.api.repository;

import com.outscout.api.entity.Campsite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampsiteRepository extends JpaRepository<Campsite, Long> {
    
    @Query("SELECT c FROM Campsite c WHERE c.park.region = :region")
    Page<Campsite> findByRegion(@Param("region") String region, Pageable pageable);
    
    @Query("SELECT c FROM Campsite c WHERE c.petAllowed = :petAllowed")
    Page<Campsite> findByPetAllowed(@Param("petAllowed") Boolean petAllowed, Pageable pageable);
    
    @Query("SELECT c FROM Campsite c WHERE c.park.region = :region AND c.petAllowed = :petAllowed")
    Page<Campsite> findByRegionAndPetAllowed(@Param("region") String region, 
                                           @Param("petAllowed") Boolean petAllowed, 
                                           Pageable pageable);
    
    @Query(value = "SELECT * FROM campsites c " +
                   "WHERE ST_Distance_Sphere(POINT(c.lon, c.lat), POINT(:lon, :lat)) <= :radius",
           nativeQuery = true)
    List<Campsite> findByLocationWithinRadius(@Param("lat") Double lat, 
                                            @Param("lon") Double lon, 
                                            @Param("radius") Double radius);
}
