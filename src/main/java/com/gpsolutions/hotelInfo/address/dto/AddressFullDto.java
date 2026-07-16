package com.gpsolutions.hotelInfo.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressFullDto {
    @NotNull(message = "Номер дома обязателен")
    private Integer houseNumber;

    @NotBlank(message = "Улица не может быть пустой")
    private String street;

    @NotBlank(message = "Город не может быть пустым")
    private String city;

    @NotBlank(message = "Страна не может быть пустой")
    private String country;

    @NotBlank(message = "Почтовый индекс не может быть пустым")
    private String postCode;
}
