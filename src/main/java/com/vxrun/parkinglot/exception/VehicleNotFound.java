package com.vxrun.parkinglot.exception;

public class VehicleNotFound extends Exception {

    public VehicleNotFound(){
        super("Vehicle not found in parking lot.. please ensure correct vehicle number");
    }
}
