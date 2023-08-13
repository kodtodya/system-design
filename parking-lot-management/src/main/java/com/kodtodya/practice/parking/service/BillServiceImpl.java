package com.kodtodya.practice.parking.service;

import com.kodtodya.practice.parking.enums.VehicleType;
import com.kodtodya.practice.parking.model.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class BillServiceImpl implements BillService {

    @Value("${parking.charges.weekday.2wheeler}")
    private int weekdayChargesFor2WheelerPerHour;

    @Value("${parking.charges.weekday.4wheeler}")
    private int weekdayChargesFor4WheelerPerHour;

    @Value("${parking.charges.weekend.2wheeler}")
    private int weekendChargesFor2WheelerPerHour;

    @Value("${parking.charges.weekend.4wheeler}")
    private int weekendChargesFor4WheelerPerHour;

    @Override
    public int generateBill(Ticket ticket) {
        long totalSeconds = (System.currentTimeMillis() - ticket.getParkingTime().getTime()) / 1000;
        int hours = (int) Math.ceil((double) totalSeconds / 3600);

        int chargesPerHour;
        if (this.isWeekend(ticket.getParkingTime())) {
            chargesPerHour = (ticket.getVehicleType().equals(VehicleType.TWO_WHEELER))
                    ? weekendChargesFor2WheelerPerHour
                    : weekendChargesFor4WheelerPerHour;
        } else {
            chargesPerHour = (ticket.getVehicleType().equals(VehicleType.TWO_WHEELER))
                    ? weekdayChargesFor2WheelerPerHour
                    : weekdayChargesFor4WheelerPerHour;
        }

        return hours * chargesPerHour;
    }

    private boolean isWeekend(Date currentDate) {
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        String dayOfWeekName = dayOfWeek.name();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }
}
