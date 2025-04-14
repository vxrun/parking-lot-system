package com.vxrun.parkinglot.dto;

import com.vxrun.parkinglot.utils.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vehicle {

    private String vehicleNumber;
    private VehicleType vehicleType;
}
