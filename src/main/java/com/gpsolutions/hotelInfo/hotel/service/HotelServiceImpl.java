package com.gpsolutions.hotelInfo.hotel.service;

import com.gpsolutions.hotelInfo.address.model.Address;
import com.gpsolutions.hotelInfo.amenity.dao.AmenityRepository;
import com.gpsolutions.hotelInfo.amenity.model.Amenity;
import com.gpsolutions.hotelInfo.contacts.model.Contacts;
import com.gpsolutions.hotelInfo.exception.BadRequestException;
import com.gpsolutions.hotelInfo.exception.NotFoundException;
import com.gpsolutions.hotelInfo.hotel.dao.HotelRepository;
import com.gpsolutions.hotelInfo.hotel.dto.HotelFullDto;
import com.gpsolutions.hotelInfo.hotel.dto.HotelShortDto;
import com.gpsolutions.hotelInfo.hotel.dto.NewHotelDto;
import com.gpsolutions.hotelInfo.hotel.mapper.HotelMapper;
import com.gpsolutions.hotelInfo.hotel.model.ArrivalTime;
import com.gpsolutions.hotelInfo.hotel.model.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;

    @Override
    public Collection<HotelShortDto> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .map(HotelMapper::mapToHotelShortDto)
                .toList();
    }

    @Override
    public HotelFullDto getAllInformationByHotel(Long hotelId) {
        Hotel hotel = validateHotel(hotelId);

        return HotelMapper.mapToHotelFullDto(hotel);
    }

    @Override
    public Collection<HotelShortDto> getAllEventsByParameters(String name,
                                                              String brand,
                                                              String city,
                                                              String country,
                                                              List<String> amenities) {
        List<String> lowerAmenities = null;
        if (amenities != null && !amenities.isEmpty()) {
            lowerAmenities = amenities.stream()
                    .map(String::toLowerCase)
                    .toList();
        }

        List<Hotel> hotels = hotelRepository.searchHotels(name, brand, city, country, lowerAmenities);

        return hotels.stream()
                .map(HotelMapper::mapToHotelShortDto)
                .toList();
    }

    @Override
    @Transactional
    public HotelShortDto createHotel(NewHotelDto request) {
        Address address = Address.builder()
                .houseNumber(request.getAddress().getHouseNumber())
                .street(request.getAddress().getStreet())
                .city(request.getAddress().getCity())
                .country(request.getAddress().getCountry())
                .postCode(request.getAddress().getPostCode())
                .build();

        Contacts contacts = Contacts.builder()
                .phone(request.getContacts().getPhone())
                .email(request.getContacts().getEmail())
                .build();

        ArrivalTime arrivalTime = null;
        if (request.getArrivalTime() != null) {
            arrivalTime = ArrivalTime.builder()
                    .checkIn(request.getArrivalTime().getCheckIn())
                    .checkOut(request.getArrivalTime().getCheckOut())
                    .build();
        }

        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .brand(request.getBrand())
                .address(address)
                .contacts(contacts)
                .arrivalTime(arrivalTime)
                .build();

        Hotel savedHotel = hotelRepository.save(hotel);

        return HotelMapper.mapToHotelShortDto(savedHotel);
    }

    @Override
    @Transactional
    public void addAmenitiesToHotel(Long hotelId, List<String> amenities) {
        Hotel hotel = validateHotel(hotelId);
        Set<Amenity> currentAmenities = hotel.getAmenities();

        for (String name : amenities) {
            if (name == null || name.isBlank()) {
                continue;
            }

            Amenity amenity = amenityRepository.findByNameIgnoreCase(name.trim())
                    .orElseGet(() -> {
                        Amenity newAmenity = Amenity.builder()
                                .name(name.trim())
                                .build();
                        return amenityRepository.save(newAmenity);
                    });

            currentAmenities.add(amenity);
        }

        hotelRepository.save(hotel);
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        List<Object[]> countHotels = switch (param.toLowerCase().trim()) {
            case "brand" -> hotelRepository.countHotelsByBrand();
            case "city" -> hotelRepository.countHotelsByCity();
            case "country" -> hotelRepository.countHotelsByCountry();
            case "amenities" -> amenityRepository.countHotelsByAmenities();
            default -> throw new BadRequestException("Неверный параметр histogram: " + param);
        };

        return countHotels.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }

    private Hotel validateHotel(Long hotelId) {
        if (hotelId == null) {
            throw new BadRequestException("HotelId не может быть null.");
        }

        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new NotFoundException("Не найден отель с id = " + hotelId));
    }
}
