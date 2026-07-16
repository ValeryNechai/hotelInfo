package com.gpsolutions.hotelInfo.amenity.mapper;

import com.gpsolutions.hotelInfo.amenity.dto.AmenityDto;
import com.gpsolutions.hotelInfo.amenity.model.Amenity;

public final class AmenityMapper {
    public static AmenityDto mapToAmenityDto(Amenity amenity) {
        return AmenityDto.builder()
                .name(amenity.getName())
                .build();
    }
}
