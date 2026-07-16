package com.gpsolutions.hotelInfo.hotel.dto;

import com.gpsolutions.hotelInfo.address.dto.AddressShortDto;
import com.gpsolutions.hotelInfo.contacts.dto.ContactsShortDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Краткая информация об отеле")
public class HotelShortDto {
    private Long id;
    private String name;
    private String description;
    private AddressShortDto address;
    private ContactsShortDto phone;
}
