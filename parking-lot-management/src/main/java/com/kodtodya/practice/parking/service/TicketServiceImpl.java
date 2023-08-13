package com.kodtodya.practice.parking.service;

import com.kodtodya.practice.parking.model.ParkingSlot;
import com.kodtodya.practice.parking.model.Ticket;
import com.kodtodya.practice.parking.model.Vehicle;
import com.kodtodya.practice.parking.repository.TicketRepository;
import com.kodtodya.practice.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    @Override
    public Ticket issueTicket(Vehicle vehicle, ParkingSlot parkingSlot) {
        Ticket ticket = Ticket.builder()
                .parkedVehicle(vehicle)
                .parkingTime(new Date())
                .parkingSlot(parkingSlot)
                .vehicleType(vehicle.getVehicleType())
                .build();

        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public void persistParkingCharges(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Ticket findTicketByVehicle(Vehicle vehicle) {
        return ticketRepository.findByVehicle(vehicle).orElse(null);
    }

    @Override
    public Ticket findTicketByTicketNumber(Long ticketNumber) {
        return ticketRepository.findById(ticketNumber).orElse(null);
    }
}
