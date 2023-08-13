package com.kodtodya.practice.parking.repository;

import com.kodtodya.practice.parking.enums.VehicleType;
import com.kodtodya.practice.parking.model.ParkingSlot;
import com.kodtodya.practice.parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {
    @Query("SELECT ps FROM ParkingSlot ps WHERE ps.parkedVehicle = :vehicle")
    Optional<ParkingSlot> findByVehicle(@Param("vehicle") Vehicle vehicle);

    @Query("SELECT ps FROM ParkingSlot ps WHERE ps.parkedVehicle IS NOT NULL ORDER BY ps.vehicleType")
    Set<ParkingSlot> findCurrentOccupiedParkingSlots();

    @Query("SELECT ps FROM ParkingSlot ps WHERE ps.parkedVehicle = NULL AND ps.vehicleType = :vehicleType")
    Set<ParkingSlot> findCurrentAvailableParkingSlots(@Param("vehicleType") VehicleType vehicleType);
}
