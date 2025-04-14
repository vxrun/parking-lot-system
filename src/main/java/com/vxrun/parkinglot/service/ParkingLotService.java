package com.vxrun.parkinglot.service;

import com.vxrun.parkinglot.dto.Ticket;
import com.vxrun.parkinglot.dto.Vehicle;
import com.vxrun.parkinglot.exception.ParkingNotAvailable;
import com.vxrun.parkinglot.exception.VehicleNotFound;

public interface ParkingLotService {
    void initializeParkingLot(int twoWheelerSpace, int fourWheelerSpace);

    Ticket parkVehicle(Vehicle vehicle) throws ParkingNotAvailable;

    Long unparkVehicle(Ticket ticket) throws ParkingNotAvailable, VehicleNotFound;
}

