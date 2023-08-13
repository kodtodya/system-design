package com.kodtodya.practice.parking.service;

import com.kodtodya.practice.parking.enums.VehicleType;
import com.kodtodya.practice.parking.exception.NoParkingSlotAvailableException;
import com.kodtodya.practice.parking.exception.NoVehicleParkedException;
import com.kodtodya.practice.parking.exception.ParkingFullException;
import com.kodtodya.practice.parking.model.ParkingSlot;
import com.kodtodya.practice.parking.model.Ticket;
import com.kodtodya.practice.parking.model.Vehicle;
import com.kodtodya.practice.parking.repository.ParkingSlotRepository;
import com.kodtodya.practice.parking.repository.VehicleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BillService billService;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Value("${parking.level.slots.2wheeler}")
    private int total2WheelerSlots;

    @Value("${parking.level.slots.2wheeler}")
    private int total4WheelerSlots;

    @PostConstruct
    public void init() {
        initializeParkingOccupancy(this.total2WheelerSlots, VehicleType.TWO_WHEELER);
        initializeParkingOccupancy(this.total4WheelerSlots, VehicleType.FOUR_WHEELER);
    }

    private void initializeParkingOccupancy(int parkingCount, VehicleType vehicleType) {
        for (int counter = 1; counter <= parkingCount; counter++) {
            ParkingSlot slot = ParkingSlot.builder()
                    .slotNumber((vehicleType.equals(VehicleType.TWO_WHEELER) ? 200 : 400) + counter)
                    .vehicleType(vehicleType)
                    .isEmpty(true)
                    .build();
            parkingSlotRepository.saveAndFlush(slot);
        }
    }

    @Transactional
    @Override
    public Ticket park(Vehicle vehicle) throws ParkingFullException, NoParkingSlotAvailableException {
        // Step-1: identify vacant parking slot first
        ParkingSlot parkingSlot = this.getNextAvailableParkingSlot(vehicle.getVehicleType());
        if (parkingSlot == null) {
            throw new ParkingFullException("Entire parking is full for " + vehicle.getVehicleType() + " type of vehicles. Please try once any other parking slot vacates.");
        } else {
            parkingSlot.setEmpty(false);
            parkingSlot.setParkedVehicle(vehicle);

            // step-2: park the vehicle
            parkingSlotRepository.save(parkingSlot);

            // step-3: issue the ticket
            return ticketService.issueTicket(vehicle, parkingSlot);
        }
    }

    @Transactional
    @Override
    public Ticket vacate(String vehicleNumber) throws NoVehicleParkedException {
        Vehicle vehicle = vehicleRepository.findById(vehicleNumber).orElse(null);
        ParkingSlot parkingSlot = this.findParkingSlotByVehicle(vehicle);
        if (parkingSlot == null) {
            throw new NoVehicleParkedException("No Vehicle is parked with Vehicle No." + vehicleNumber);
        }
        parkingSlot.setEmpty(true);
        parkingSlot.setParkedVehicle(null);

        Ticket ticket = ticketService.findTicketByVehicle(vehicle);
        ticket.setParkingVacateTime(new Date());
        int charges = billService.generateBill(ticket);
        ticket.setParkingCharges(charges);
        ticketService.persistParkingCharges(ticket);
        parkingSlotRepository.save(parkingSlot);
        return ticket;
    }

    @Override
    public void vacateEntireParking() {
        Set<ParkingSlot> parkingSlots = parkingSlotRepository.findCurrentOccupiedParkingSlots();
        parkingSlots.stream().forEach(parkingSlot -> {
            Ticket ticket = ticketService.findTicketByVehicle(parkingSlot.getParkedVehicle());
            ticket.setParkingVacateTime(new Date());
            int charges = billService.generateBill(ticket);
            ticket.setParkingCharges(charges);
            ticketService.persistParkingCharges(ticket);
            parkingSlot.setEmpty(true);
            parkingSlot.setParkedVehicle(null);
            parkingSlotRepository.save(parkingSlot);
        });
    }

    @Override
    public ParkingSlot getNextAvailableParkingSlot(VehicleType vehicleType) throws NoParkingSlotAvailableException {
        Set<ParkingSlot> nextEmptyParkingSlots = parkingSlotRepository.findCurrentAvailableParkingSlots(vehicleType);
        if (!nextEmptyParkingSlots.isEmpty()) {
            return nextEmptyParkingSlots.stream().findFirst().get();
        } else {
            throw new NoParkingSlotAvailableException("No Parking slot available at this moment!!");
        }
    }

    public Set<ParkingSlot> getCurrentParkingStatus() {
        return parkingSlotRepository.findCurrentOccupiedParkingSlots();
    }

    private ParkingSlot findParkingSlotByVehicle(Vehicle vehicle) {
        return parkingSlotRepository.findByVehicle(vehicle).orElse(null);
    }
}
