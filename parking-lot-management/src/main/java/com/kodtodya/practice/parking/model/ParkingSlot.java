package com.kodtodya.practice.parking.model;

import com.kodtodya.practice.parking.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "ParkingSlot")
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlot {
    @Id
    @Column(name = "SlotNumber", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotNumber;

    @Column(name="VehicleType", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private boolean isEmpty;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "VehicleNumber", referencedColumnName = "VehicleNumber")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @PrimaryKeyJoinColumn(columnDefinition="VehicleNumber")
    private Vehicle parkedVehicle;
}
