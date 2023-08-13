package com.kodtodya.practice.parking.model;

import com.kodtodya.practice.parking.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "Vehicle")
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    private String vehicleNumber;

    @Column(name="VehicleType", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
