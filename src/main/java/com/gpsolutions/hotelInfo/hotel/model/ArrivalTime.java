package com.gpsolutions.hotelInfo.hotel.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArrivalTime {
    @Column(name = "check_in")
    private String checkIn;

    @Column(name = "check_out")
    private String checkOut;
}
