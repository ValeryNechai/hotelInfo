package com.gpsolutions.hotelInfo.hotel.dto;

import com.gpsolutions.hotelInfo.address.dto.AddressFullDto;
import com.gpsolutions.hotelInfo.contacts.dto.ContactsFullDto;
import com.gpsolutions.hotelInfo.hotel.model.ArrivalTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Информация об отеле, необходимая для его создания")
public class NewHotelDto {
    @NotBlank(message = "Имя отеля не может быть пустым")
    private String name;

    private String description;

    @NotBlank(message = "Бренд отеля не может быть пустым")
    private String brand;

    @NotNull(message = "Адрес обязателен для заполнения")
    @Valid
    private AddressFullDto address;

    @NotNull(message = "Контакты обязательны для заполнения")
    @Valid
    private ContactsFullDto contacts;

    private ArrivalTime arrivalTime;
}
