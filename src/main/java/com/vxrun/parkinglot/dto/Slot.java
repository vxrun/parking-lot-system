package com.vxrun.parkinglot.dto;

import lombok.Data;

@Data
public class Slot {

    private Integer slotNumber;
    private boolean isEmpty = true;
    private Vehicle parkedVehicle;

    public Slot(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

}
