package com.gpsolutions.hotelInfo.address.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Развернутые данные адреса отеля")
public class AddressFullDto {
    @Schema(description = "Номер дома", example = "9")
    @NotNull(message = "Номер дома обязателен")
    private Integer houseNumber;

    @Schema(description = "Улица", example = "Проспект Победителей")
    @NotBlank(message = "Улица не может быть пустой")
    private String street;

    @Schema(description = "Город", example = "Минск")
    @NotBlank(message = "Город не может быть пустым")
    private String city;

    @Schema(description = "Страна", example = "Беларусь")
    @NotBlank(message = "Страна не может быть пустой")
    private String country;

    @Schema(description = "Почтовый индекс", example = "220004")
    @NotBlank(message = "Почтовый индекс не может быть пустым")
    private String postCode;
}
