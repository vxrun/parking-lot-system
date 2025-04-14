package com.vxrun.parkinglot.dto;

import com.vxrun.parkinglot.utils.VehicleType;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class RequestDTO {
    @NonNull
    public String vehicleNumber;
    @NonNull
    public String vehicleType;
    private Integer slotNumber;
    private String date;
}
