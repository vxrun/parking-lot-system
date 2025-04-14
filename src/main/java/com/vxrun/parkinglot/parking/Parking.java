package com.vxrun.parkinglot.parking;

import com.vxrun.parkinglot.dto.Slot;
import com.vxrun.parkinglot.exception.ParkingNotAvailable;

import java.util.List;

public interface Parking {

    List<Slot> getTwoWheelerParking() throws ParkingNotAvailable;

    List<Slot> getFourWheelerParking() throws ParkingNotAvailable;

    void initializeParking(int twoWheelerParkingSize, int fourWheelerParkingSize);
}
