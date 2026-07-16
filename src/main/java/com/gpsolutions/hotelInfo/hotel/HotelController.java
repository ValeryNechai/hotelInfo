package com.gpsolutions.hotelInfo.hotel;

import com.gpsolutions.hotelInfo.hotel.dto.HotelFullDto;
import com.gpsolutions.hotelInfo.hotel.dto.HotelShortDto;
import com.gpsolutions.hotelInfo.hotel.dto.NewHotelDto;
import com.gpsolutions.hotelInfo.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "HotelInfo", description = "API для управления отелями, их контактами и удобствами")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/hotels")
    @Operation(summary = "Получение списка всех отелей с их краткой информацией",
            description = "Возвращает краткую информацию обо всех внесенных отелях")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Найдены отели"),
    })
    public Collection<HotelShortDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Получение расширенной информации по конкретному отелю",
            description = "Возвращает полную информацию об отеле, включая адрес и контакты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Отель успешно найден"),
            @ApiResponse(responseCode = "404", description = "Отеля с таким ID не существует"),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно")
        })
    public HotelFullDto getAllInformationByHotel(
            @Parameter(description = "Id отеля", example = "1")
            @PathVariable(name = "id") Long hotelId) {
        return hotelService.getAllInformationByHotel(hotelId);
    }

    @GetMapping("/search")
    @Operation(summary = "Получение списка всех отелей с их краткой информацией по параметрам",
            description = "Возвращает краткую информацию об отелях с найденными параметрами")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Отель успешно найден"),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно")
    })
    public Collection<HotelShortDto> getAllEventsByParameters(
            @Parameter(description = "Name отеля для поиска", example = "DoubleTree by Hilton Minsk")
            @RequestParam(required = false) String name,
            @Parameter(description = "Brand отеля для поиска", example = "Hilton")
            @RequestParam(required = false) String brand,
            @Parameter(description = "City отеля для поиска", example = "Minsk")
            @RequestParam(required = false) String city,
            @Parameter(description = "Country отеля для поиска", example = "Belarus")
            @RequestParam(required = false) String country,
            @Parameter(description = "Amenities отеля для поиска", example = "Free parking, Free WiFi")
            @RequestParam(required = false) List<String> amenities) {
        return hotelService.getAllEventsByParameters(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels")
    @Operation(summary = "Cоздание нового отеля",
            description = "Возвращает полную информацию о новом отеле")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Отель успешно создан"),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно")
    })
    public HotelShortDto createHotel(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные создаваемого отеля")
            @Valid @RequestBody NewHotelDto request) {
        return hotelService.createHotel(request);
    }

    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление списка amenities к отелю",
            description = "Невозвратный метод")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Amenities успешно добавлены"),
            @ApiResponse(responseCode = "404", description = "Отеля с таким ID не существует"),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно")
    })
    public void addAmenitiesToHotel(
            @Parameter(description = "Id отеля", example = "1")
            @PathVariable(name = "id") Long hotelId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Данные добавляемых удобств отеля")
            @RequestBody List<String> amenities) {
        hotelService.addAmenitiesToHotel(hotelId, amenities);
    }

    @GetMapping("/histogram/{param}")
    @Operation(summary = "Получение количества отелей сгруппированных по каждому значению указанного параметра",
            description = "Возвращает название параметра с количество отелей")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Amenities успешно добавлены"),
            @ApiResponse(responseCode = "400", description = "Запрос составлен некорректно")
    })
    public Map<String, Long> getHistogram(
            @Parameter(description = "Название параметра", example = "brand")
            @PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
