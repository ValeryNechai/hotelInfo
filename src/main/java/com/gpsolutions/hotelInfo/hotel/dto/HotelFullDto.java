package com.gpsolutions.hotelInfo.hotel.dto;

import com.gpsolutions.hotelInfo.address.dto.AddressFullDto;
import com.gpsolutions.hotelInfo.amenity.dto.AmenityDto;
import com.gpsolutions.hotelInfo.contacts.dto.ContactsFullDto;
import com.gpsolutions.hotelInfo.hotel.model.ArrivalTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Полная информация об отеле")
public class HotelFullDto {
    private Long id;
    private String name;
    private String brand;
    private AddressFullDto address;
    private ContactsFullDto contacts;
    private ArrivalTime arrivalTime;
    private Set<String> amenities;
}
