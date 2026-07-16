package com.gpsolutions.hotelInfo.contacts.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactsFullDto {
    @NotBlank(message = "Телефон не может быть пустым")
    private String phone;

    @NotBlank(message = "Email не может быть пустым")
    private String email;
}
