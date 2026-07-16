package com.gpsolutions.hotelInfo.hotel.dto;

import com.gpsolutions.hotelInfo.address.dto.AddressShortDto;
import com.gpsolutions.hotelInfo.contacts.dto.ContactsShortDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelShortDto {
    private Long id;
    private String name;
    private String description;
    private AddressShortDto address;
    private ContactsShortDto phone;
}
