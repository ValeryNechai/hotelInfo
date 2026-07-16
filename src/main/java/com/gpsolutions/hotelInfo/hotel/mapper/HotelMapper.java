package com.gpsolutions.hotelInfo.hotel.mapper;

import com.gpsolutions.hotelInfo.address.mapper.AddressMapper;
import com.gpsolutions.hotelInfo.amenity.mapper.AmenityMapper;
import com.gpsolutions.hotelInfo.contacts.mapper.ContactsMapper;
import com.gpsolutions.hotelInfo.hotel.dto.HotelFullDto;
import com.gpsolutions.hotelInfo.hotel.dto.HotelShortDto;
import com.gpsolutions.hotelInfo.hotel.dto.NewHotelDto;
import com.gpsolutions.hotelInfo.hotel.model.Hotel;

import java.util.stream.Collectors;

public final class HotelMapper {
    public static HotelShortDto mapToHotelShortDto(Hotel hotel) {
        return HotelShortDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(AddressMapper.mapToAddressShortDto(hotel.getAddress()))
                .phone(ContactsMapper.mapToContactsShortDto(hotel.getContacts()))
                .build();
    }

    public static HotelFullDto mapToHotelFullDto(Hotel hotel) {
        return HotelFullDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .brand(hotel.getBrand())
                .address(AddressMapper.mapToAddressFullDto(hotel.getAddress()))
                .contacts(ContactsMapper.mapToContactsFullDto(hotel.getContacts()))
                .arrivalTime(hotel.getArrivalTime())
                .amenities(
                        hotel.getAmenities().stream()
                                .map(AmenityMapper::mapToAmenityDto)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
