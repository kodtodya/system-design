package com.kodtodya.practice.parking.service;

import com.kodtodya.practice.parking.model.Ticket;

public interface BillService {
    int generateBill(Ticket ticket);
}
