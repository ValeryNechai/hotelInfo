package com.gpsolutions.hotelInfo.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Полная информация о контактах отеля")
public class ContactsFullDto {
    @NotBlank(message = "Телефон не может быть пустым")
    private String phone;

    @NotBlank(message = "Email не может быть пустым")
    private String email;
}
