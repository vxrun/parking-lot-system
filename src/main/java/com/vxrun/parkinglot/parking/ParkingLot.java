package com.vxrun.parkinglot.parking;

import com.vxrun.parkinglot.dto.Slot;
import com.vxrun.parkinglot.exception.ParkingNotAvailable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//@Data
@Service
public class ParkingLot implements Parking {

    private static ParkingLot parkingLot;

    private final List<Slot> twoWheelerParking;
    private final List<Slot> fourWheelerParking;

    public ParkingLot() {
        twoWheelerParking = new ArrayList<>();
        fourWheelerParking = new ArrayList<>();
    }

    public ParkingLot getParkingLot() {
        if (parkingLot == null) {
            parkingLot = new ParkingLot();
        }
        return parkingLot;
    }

    @Override
    public List<Slot> getTwoWheelerParking() throws ParkingNotAvailable {
        List<Slot> twoWheelerParking1 = getParkingLot().twoWheelerParking;
        if (twoWheelerParking1.isEmpty()) {
            throw new ParkingNotAvailable();
        }
        return twoWheelerParking1;
    }

    @Override
    public List<Slot> getFourWheelerParking() throws ParkingNotAvailable {
        List<Slot> fourWheelerParking1 = getParkingLot().fourWheelerParking;
        if (fourWheelerParking1.isEmpty()) {
            throw new ParkingNotAvailable();
        }
        return fourWheelerParking1;
    }

    @Override
    public void initializeParking(int twoWheelerParkingSize, int fourWheelerParkingSize) {
        for (int i = 0; i < twoWheelerParkingSize; i++) {
            getParkingLot().twoWheelerParking.add(new Slot(i));
        }
        log.info("Initialized Two Wheeler Parking with Size : {} ", parkingLot.twoWheelerParking.size());
        for (int i = 0; i < fourWheelerParkingSize; i++) {
            getParkingLot().fourWheelerParking.add(new Slot(i));
        }
        log.info("Initialized Four Wheeler Parking with Size : {} ", parkingLot.fourWheelerParking.size());
    }


}
