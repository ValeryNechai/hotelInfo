package com.gpsolutions.hotelInfo.hotel.service;

import com.gpsolutions.hotelInfo.hotel.dto.HotelFullDto;
import com.gpsolutions.hotelInfo.hotel.dto.HotelShortDto;
import com.gpsolutions.hotelInfo.hotel.dto.NewHotelDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface HotelService {
    Collection<HotelShortDto> getAllHotels();

    HotelFullDto getAllInformationByHotel(Long hotelId);

    Collection<HotelShortDto> getAllEventsByParameters(
            String name, String brand, String city, String country, List<String> amenities
    );

    HotelShortDto createHotel(NewHotelDto request);

    void addAmenitiesToHotel(Long hotelId, List<String> amenities);

    Map<String, Long> getHistogram(String param);
}
