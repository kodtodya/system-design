package com.kodtodya.practice.parking.service;

import com.kodtodya.practice.parking.enums.VehicleType;
import com.kodtodya.practice.parking.exception.NoParkingSlotAvailableException;
import com.kodtodya.practice.parking.exception.NoVehicleParkedException;
import com.kodtodya.practice.parking.exception.ParkingFullException;
import com.kodtodya.practice.parking.model.ParkingSlot;
import com.kodtodya.practice.parking.model.Ticket;
import com.kodtodya.practice.parking.model.Vehicle;

import java.util.Set;

public interface ParkingService {
    Ticket park(Vehicle vehicle) throws ParkingFullException, NoParkingSlotAvailableException;

    Ticket vacate(String vehicleNumber) throws NoVehicleParkedException;

    ParkingSlot getNextAvailableParkingSlot(VehicleType vehicleType) throws NoParkingSlotAvailableException;

    Set<ParkingSlot> getCurrentParkingStatus();

    void vacateEntireParking();
}
