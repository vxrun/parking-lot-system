package com.vxrun.parkinglot.exception;

public class ParkingNotAvailable extends Exception {

    public ParkingNotAvailable(){
        super("No parking available for the required vehicle...");
    }
}
