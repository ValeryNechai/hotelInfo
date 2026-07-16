package com.gpsolutions.hotelInfo.contacts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Частичная информация о контактах отеля")
public class ContactsShortDto {
    private String phone;
}
