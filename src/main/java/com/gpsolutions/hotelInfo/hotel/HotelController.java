package com.gpsolutions.hotelInfo.hotel;

import com.gpsolutions.hotelInfo.hotel.dto.HotelFullDto;
import com.gpsolutions.hotelInfo.hotel.dto.HotelShortDto;
import com.gpsolutions.hotelInfo.hotel.dto.NewHotelDto;
import com.gpsolutions.hotelInfo.hotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/property-view")
@Validated
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/hotels")
    public Collection<HotelShortDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    public HotelFullDto getAllInformationByHotel(@PathVariable(name = "id") Long hotelId) {
        return hotelService.getAllInformationByHotel(hotelId);
    }

    @GetMapping("/search")
    public Collection<HotelShortDto> getAllEventsByParameters(@RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String brand,
                                                              @RequestParam(required = false) String city,
                                                              @RequestParam(required = false) String country,
                                                              @RequestParam(required = false) List<String> amenities) {
        return hotelService.getAllEventsByParameters(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels")
    public HotelShortDto createHotel(@Valid @RequestBody NewHotelDto request) {
        return hotelService.createHotel(request);
    }

    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.OK)
    public void addAmenitiesToHotel(@PathVariable(name = "id") Long hotelId,
                                    @RequestBody List<String> amenities) {
        hotelService.addAmenitiesToHotel(hotelId, amenities);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
