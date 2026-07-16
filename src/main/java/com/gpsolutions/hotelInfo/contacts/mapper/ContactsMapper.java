package com.gpsolutions.hotelInfo.contacts.mapper;

import com.gpsolutions.hotelInfo.contacts.dto.ContactsFullDto;
import com.gpsolutions.hotelInfo.contacts.dto.ContactsShortDto;
import com.gpsolutions.hotelInfo.contacts.model.Contacts;

public final class ContactsMapper {
    public static ContactsShortDto mapToContactsShortDto(Contacts contacts) {
        return ContactsShortDto.builder()
                .phone(contacts.getPhone())
                .build();
    }

    public static ContactsFullDto mapToContactsFullDto(Contacts contacts) {
        return ContactsFullDto.builder()
                .phone(contacts.getPhone())
                .email(contacts.getEmail())
                .build();
    }
}
