package com.kodtodya.practice.parking.service;

import com.kodtodya.practice.parking.model.ParkingSlot;
import com.kodtodya.practice.parking.model.Ticket;
import com.kodtodya.practice.parking.model.Vehicle;

public interface TicketService {
    Ticket issueTicket(Vehicle vehicle, ParkingSlot parkingSlot);

    void persistParkingCharges(Ticket ticket);

    Ticket findTicketByVehicle(Vehicle vehicle);

    Ticket findTicketByTicketNumber(Long ticketNumber);
}
