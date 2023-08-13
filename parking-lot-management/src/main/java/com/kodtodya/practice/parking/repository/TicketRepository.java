package com.kodtodya.practice.parking.repository;

import com.kodtodya.practice.parking.model.Ticket;
import com.kodtodya.practice.parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.parkedVehicle = :vehicle")
    Optional<Ticket> findByVehicle(@Param("vehicle") Vehicle vehicle);

}
