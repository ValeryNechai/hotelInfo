package com.gpsolutions.hotelInfo.address.mapper;

import com.gpsolutions.hotelInfo.address.dto.AddressFullDto;
import com.gpsolutions.hotelInfo.address.dto.AddressShortDto;
import com.gpsolutions.hotelInfo.address.model.Address;

public final class AddressMapper {
    public static AddressShortDto mapToAddressShortDto(Address address) {
        return AddressShortDto.builder()
                .address(String.format(
                        "%d %s, %s, %s, %s",
                        address.getHouseNumber(),
                        address.getStreet(),
                        address.getCity(),
                        address.getPostCode(),
                        address.getCountry()))
                .build();
    }

    public static AddressFullDto mapToAddressFullDto (Address address) {
        return AddressFullDto.builder()
                .houseNumber(address.getHouseNumber())
                .street(address.getStreet())
                .city(address.getCity())
                .country(address.getCountry())
                .postCode(address.getPostCode())
                .build();
    }
}
