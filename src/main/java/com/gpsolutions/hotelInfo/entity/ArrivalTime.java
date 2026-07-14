package com.gpsolutions.hotelInfo.entity;

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
