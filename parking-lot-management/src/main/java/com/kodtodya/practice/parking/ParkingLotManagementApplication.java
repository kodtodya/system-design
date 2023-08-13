package com.kodtodya.practice.parking;

import com.kodtodya.practice.parking.enums.VehicleType;
import com.kodtodya.practice.parking.model.ParkingSlot;
import com.kodtodya.practice.parking.model.Ticket;
import com.kodtodya.practice.parking.model.Vehicle;
import com.kodtodya.practice.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;
import java.util.Set;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.kodtodya.practice.parking")
public class ParkingLotManagementApplication implements ApplicationRunner {

    @Autowired
    private ParkingService parkingService;

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotManagementApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean continueExecutionFlag = true;
        do {
            System.out.println("\n--------------------------------------------\n");
            System.out.println("Welcome to parking management system.");
            System.out.println("Please follow the instructions:");
            System.out.println("1. Check parking availability status");
            System.out.println("2. Park vehicle");
            System.out.println("3. Vacate parking");
            System.out.println("4. Vacate Entire Parking");
            System.out.println("5. Check Current Parking Occupancy");
            System.out.println("6. Exit the system");
            System.out.print("\n** Please enter the menu: ");
            Scanner scanner = new Scanner(System.in);
            try {
                int option = scanner.nextInt();
                String vehicleNumber;
                ParkingSlot parkingSlot;
                switch (option) {
                    case 1 -> {
                        System.out.print("\n* Enter type of vehicle - [1. Two wheeler \t 2. Four Wheeler]:");
                        option = scanner.nextInt();
                        parkingSlot = parkingService.getNextAvailableParkingSlot(option == 1 ? VehicleType.TWO_WHEELER : VehicleType.FOUR_WHEELER);
                        System.out.println(parkingSlot == null ? "No Parking slot available" : "Parking Slot No." + parkingSlot.getSlotNumber() + " is available.");
                    }
                    case 2 -> {
                        System.out.println("\n* Enter type of vehicle - [1. Two wheeler \t 2. Four Wheeler]:");
                        option = scanner.nextInt();
                        System.out.print("* Enter vehicle number :");
                        vehicleNumber = scanner.next();
                        VehicleType vehicleType = option == 1 ? VehicleType.TWO_WHEELER : VehicleType.FOUR_WHEELER;
                        Vehicle vehicle = Vehicle.builder()
                                .vehicleType(vehicleType)
                                .vehicleNumber(vehicleNumber)
                                .build();

                        // create a vehicle
                        Ticket ticket = parkingService.park(vehicle);
                        System.out.println(ticket);
                    }
                    case 3 -> {
                        System.out.print("\n* Enter vehicle number :");
                        vehicleNumber = scanner.next();
                        Ticket ticket = parkingService.vacate(vehicleNumber);
                        System.out.println(ticket.getVehicleType() + " type of Parking No." + ticket.getParkingSlot().getSlotNumber() + " is vacated and charges are INR " + ticket.getParkingCharges());
                    }
                    case 4 -> {
                        parkingService.vacateEntireParking();
                        System.out.println("Entire Parking is vacated.");
                    }
                    case 5 -> {
                        Set<ParkingSlot> currentParkingOccupancies = parkingService.getCurrentParkingStatus();
                        currentParkingOccupancies.forEach(System.out::println);
                    }
                    case 6 -> {
                        System.out.println("Khatm... tata... bye, bye... gaya!!!");
                        continueExecutionFlag = false;
                    }
                }
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
            }
        } while (continueExecutionFlag);
        System.exit(0);
    }
}
