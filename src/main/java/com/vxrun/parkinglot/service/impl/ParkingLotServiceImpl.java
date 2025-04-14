package com.vxrun.parkinglot.service.impl;

import com.vxrun.parkinglot.dto.Slot;
import com.vxrun.parkinglot.dto.Ticket;
import com.vxrun.parkinglot.dto.Vehicle;
import com.vxrun.parkinglot.exception.ParkingNotAvailable;
import com.vxrun.parkinglot.exception.VehicleNotFound;
import com.vxrun.parkinglot.parking.Parking;
import com.vxrun.parkinglot.service.ParkingLotService;
import com.vxrun.parkinglot.utils.Constants;
import com.vxrun.parkinglot.utils.VehicleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    Parking parking;

    @Override
    public void initializeParkingLot(int twoWheelerSpace, int fourWheelerSpace) {
        parking.initializeParking(twoWheelerSpace, fourWheelerSpace);
        log.info("Initialized parking lot with space [2-Wheeler : {}, 4-Wheeler: {} ]", twoWheelerSpace, fourWheelerSpace);
    }

    @Override
    public Ticket parkVehicle(Vehicle vehicle) throws ParkingNotAvailable {
        log.info("Trying to Park : {} ", vehicle.toString());
        Slot nextAvailableSlot = getNextAvailableSlot(vehicle.getVehicleType());
        nextAvailableSlot.setParkedVehicle(vehicle);
        nextAvailableSlot.setEmpty(false);
        log.info("Found available slot : [{}] ", nextAvailableSlot.toString());
        Ticket ticket = new Ticket(nextAvailableSlot.getSlotNumber(), vehicle, new Date(), vehicle.getVehicleType());
        log.info("Ticket generated : {} ", ticket.toString());
        return ticket;
    }

    @Override
    public Long unparkVehicle(Ticket ticket) throws ParkingNotAvailable, VehicleNotFound {
        Slot slot = getParkedSlotForVehicle(ticket.getSlotNumber(), ticket.getVehicleType());
        if (!slot.getParkedVehicle().getVehicleNumber().contentEquals(ticket.getVehicle().getVehicleNumber())) {
            throw new VehicleNotFound();
        }
        slot.setParkedVehicle(null);
        slot.setEmpty(true);
        Date parkedDateTime = ticket.getDate();
        Date currDateTime = new Date();
        long diffInMillis = currDateTime.getTime() - parkedDateTime.getTime();
        long diffHours = diffInMillis / (60 * 60 * 1000) % 24;
        return diffHours *
                (ticket.getVehicleType().equals(VehicleType.TWO_WHEELER)
                        ? Constants.TWO_WHEELER_PER_HOUR_PRICE
                        : Constants.FOUR_WHEELER_PER_HOUR_PRICE);
    }

    private Slot getParkedSlotForVehicle(Integer slotNumber, VehicleType vehicleType) throws ParkingNotAvailable, VehicleNotFound {
        List<Slot> parkingSlot = null;
        if (vehicleType.equals(VehicleType.TWO_WHEELER)) {
            parkingSlot = parking.getTwoWheelerParking();
        } else if (vehicleType.equals(VehicleType.FOUR_WHEELER)) {
            parkingSlot = parking.getFourWheelerParking();
        }
        for (Slot slot : parkingSlot) {
            if (!slot.isEmpty() && (Objects.equals(slot.getSlotNumber(), slotNumber))) {
                return slot;
            }
        }
        throw new VehicleNotFound();
    }

    private Slot getNextAvailableSlot(VehicleType vehicleType) throws ParkingNotAvailable {
        List<Slot> availableSlot = null;
        if (vehicleType.equals(VehicleType.TWO_WHEELER)) {
            availableSlot = parking.getTwoWheelerParking();
        } else if (vehicleType.equals(VehicleType.FOUR_WHEELER)) {
            availableSlot = parking.getFourWheelerParking();
        }
        log.info("Found Slot : [{}] ", availableSlot);
        for (Slot slot : availableSlot) {
            if (slot.isEmpty()) {
                return slot;
            }
        }
        throw new ParkingNotAvailable();
    }
}
