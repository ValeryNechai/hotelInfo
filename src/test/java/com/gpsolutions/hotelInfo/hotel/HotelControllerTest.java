package com.gpsolutions.hotelInfo.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpsolutions.hotelInfo.address.dto.AddressFullDto;
import com.gpsolutions.hotelInfo.address.dto.AddressShortDto;
import com.gpsolutions.hotelInfo.contacts.dto.ContactsFullDto;
import com.gpsolutions.hotelInfo.contacts.dto.ContactsShortDto;
import com.gpsolutions.hotelInfo.hotel.dto.HotelShortDto;
import com.gpsolutions.hotelInfo.hotel.dto.NewHotelDto;
import com.gpsolutions.hotelInfo.hotel.service.HotelService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(HotelController.class)
public class HotelControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HotelService hotelService;

    @SneakyThrows
    @Test
    void createHotel_whenHotelNotValid_ThenReturnedBadRequest() {
        long bookerId = 0L;
        NewHotelDto hotelToCreate = new NewHotelDto();
        hotelToCreate.setName("");
        hotelToCreate.setBrand("");

        mockMvc.perform(post("/property-view/hotels")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(hotelToCreate)))
                .andExpect(status().isBadRequest());

        verify(hotelService, never()).createHotel(hotelToCreate);
    }

    @SneakyThrows
    @Test
    void createHotel_whenHotelIsValid_ThenReturnedOk() {
        AddressFullDto address = AddressFullDto.builder()
                .houseNumber(9)
                .street("Pobediteley Avenue")
                .city("Minsk")
                .country("Belarus")
                .postCode("220004")
                .build();

        ContactsFullDto contacts = ContactsFullDto.builder()
                .phone("+375 17 309-80-00")
                .email("doubletreeminsk.info@hilton.com")
                .build();

        NewHotelDto hotelToCreate = new NewHotelDto();
        hotelToCreate.setName("DoubleTree by Hilton Minsk");
        hotelToCreate.setBrand("Hilton");
        hotelToCreate.setAddress(address);
        hotelToCreate.setContacts(contacts);

        HotelShortDto expectedHotel = HotelShortDto.builder()
                .id(1L)
                .name("DoubleTree by Hilton Minsk")
                .address(AddressShortDto.builder().address("9 Pobediteley Avenue, Minsk, 220004, Belarus").build())
                .phone(ContactsShortDto.builder().phone("+375 17 309-80-00").build())
                .build();
        when(hotelService.createHotel(hotelToCreate)).thenReturn(expectedHotel);

        String result = mockMvc.perform(post("/property-view/hotels")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(hotelToCreate)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(expectedHotel), result);
    }

    @SneakyThrows
    @Test
    void createAmenitiesByHotel_whenAmenitiesIsValid_ThenReturnedOk() {
        Long hotelId = 1L;
        List<String> amenities = Arrays.asList(
                "Free parking",
                "Free WiFi",
                "Non-smoking rooms",
                "Concierge",
                "On-site restaurant",
                "Fitness center",
                "Pet-friendly rooms",
                "Room service",
                "Business center",
                "Meeting rooms"
        );

        doNothing().when(hotelService).addAmenitiesToHotel(hotelId, amenities);

        mockMvc.perform(post("/property-view/hotels/{id}/amenities", hotelId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(amenities)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(hotelService).addAmenitiesToHotel(hotelId, amenities);
    }

    @SneakyThrows
    @Test
    void getHotelById() {
        long hotelId = 1L;

        mockMvc.perform(get("/property-view/hotels/{id}", hotelId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(hotelService).getAllInformationByHotel(hotelId);
    }

    @SneakyThrows
    @Test
    void getAllHotelsByParameters_WhenSearchByBrand_ShouldReturnFilteredHotels() {
        String brand = "Hilton";
        HotelShortDto expectedHotel = HotelShortDto.builder()
                .id(1L)
                .name("DoubleTree by Hilton Minsk")
                .address(AddressShortDto.builder().address("9 Pobediteley Avenue, Minsk, 220004, Belarus").build())
                .phone(ContactsShortDto.builder().phone("+375 17 309-80-00").build())
                .build();

        when(hotelService.getAllEventsByParameters(isNull(), eq(brand), isNull(), isNull(), isNull()))
                .thenReturn(List.of(expectedHotel));

        mockMvc.perform(get("/property-view/search")
                        .param("brand", brand)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(hotelService).getAllEventsByParameters(isNull(), eq(brand), isNull(), isNull(), isNull());
    }

    @SneakyThrows
    @Test
    void getHistogram_WhenParamIsCity_ShouldReturnCityHistogram() {
        String param = "city";
        Map<String, Long> expectedHistogram = new HashMap<>();
        expectedHistogram.put("Moscow", 10L);
        expectedHistogram.put("Minsk", 7L);
        expectedHistogram.put("Grodno", 4L);

        when(hotelService.getHistogram(eq(param))).thenReturn(expectedHistogram);

        mockMvc.perform(get("/property-view/histogram/{param}", param)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Moscow").value(10))
                .andExpect(jsonPath("$.Minsk").value(7))
                .andExpect(jsonPath("$.Grodno").value(4));

        verify(hotelService).getHistogram(eq(param));
    }
}
