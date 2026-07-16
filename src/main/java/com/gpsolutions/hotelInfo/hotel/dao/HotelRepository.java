package com.gpsolutions.hotelInfo.hotel.dao;

import com.gpsolutions.hotelInfo.hotel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h " +
            "LEFT JOIN h.amenities am " +
            "WHERE (:name IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:brand IS NULL OR LOWER(h.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) " +
            "AND (:city IS NULL OR LOWER(h.address.city) = LOWER(:city)) " +
            "AND (:country IS NULL OR LOWER(h.address.country) = LOWER(:country)) " +
            "GROUP BY h.id " +
            "HAVING :amenities IS NULL OR " +
            "COUNT(DISTINCT CASE WHEN LOWER(am.name) IN :amenities THEN am.name END) =" +
            " :#{#amenities == null ? 0 : #amenities.size()}")
    List<Hotel> searchHotels(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("city") String city,
            @Param("country") String country,
            @Param("amenities") List<String> amenities
    );

    @Query("SELECT h.brand, COUNT(h) " +
            "FROM Hotel h " +
            "WHERE h.brand IS NOT NULL " +
            "GROUP BY h.brand")
    List<Object[]> countHotelsByBrand();

    @Query("SELECT h.address.city, COUNT(h) " +
            "FROM Hotel h " +
            "WHERE h.address.city IS NOT NULL " +
            "GROUP BY h.address.city")
    List<Object[]> countHotelsByCity();

    @Query("SELECT h.address.country, COUNT(h) " +
            "FROM Hotel h " +
            "WHERE h.address.country IS NOT NULL " +
            "GROUP BY h.address.country")
    List<Object[]> countHotelsByCountry();
}
