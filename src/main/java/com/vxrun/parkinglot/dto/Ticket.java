package com.vxrun.parkinglot.dto;

import com.vxrun.parkinglot.utils.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Ticket {

    private Integer slotNumber;
    private Vehicle vehicle;
    private Date date;
    private VehicleType vehicleType;
}
