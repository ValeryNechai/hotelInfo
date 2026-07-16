package com.gpsolutions.hotelInfo.address.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "house_number")
    private Integer houseNumber;

    private String street;
    private String city;
    private String country;

    @Column(name = "post_code")
    private String postCode;
}
