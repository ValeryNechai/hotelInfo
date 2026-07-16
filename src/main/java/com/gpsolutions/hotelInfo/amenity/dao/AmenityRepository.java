package com.gpsolutions.hotelInfo.amenity.dao;

import com.gpsolutions.hotelInfo.amenity.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Optional<Amenity> findByNameIgnoreCase(String name);

    @Query("SELECT a.name, COUNT(h) " +
            "FROM Amenity a " +
            "JOIN a.hotels h " +
            "GROUP BY a.name")
    List<Object[]> countHotelsByAmenities();
}
