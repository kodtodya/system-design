package com.kodtodya.practice.parking.model;

import com.kodtodya.practice.parking.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Setter
@Getter
@Builder
@ToString
@Entity
@Table(name = "Ticket")
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TicketNumber")
    private Long ticketNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ParkingSlotNumber")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private ParkingSlot parkingSlot;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "VehicleNumber", referencedColumnName = "VehicleNumber")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @PrimaryKeyJoinColumn(columnDefinition="VehicleNumber")
    private Vehicle parkedVehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "VehicleType")
    private VehicleType vehicleType;

    @Column(name = "ParkingTime")
    private Date parkingTime;

    @Column(name = "ParkingVacateTime")
    private Date parkingVacateTime;

    @Column(name = "ParkingCharges")
    private int parkingCharges;
}
