package com.vxrun.parkinglot.controller;

import com.vxrun.parkinglot.dto.RequestDTO;
import com.vxrun.parkinglot.dto.ResponseDTO;
import com.vxrun.parkinglot.dto.Ticket;
import com.vxrun.parkinglot.dto.Vehicle;
import com.vxrun.parkinglot.exception.ParkingNotAvailable;
import com.vxrun.parkinglot.exception.VehicleNotFound;
import com.vxrun.parkinglot.service.ParkingLotService;
import com.vxrun.parkinglot.utils.Constants;
import com.vxrun.parkinglot.utils.VehicleType;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/parking")

public class ParkingController {

    @Autowired
    ParkingLotService parkingLotService;

    @GetMapping("/park")
    public ResponseDTO parkVehicle(RequestDTO requestDTO) {
        String vehicleType = requestDTO.getVehicleType();
        VehicleType vehicleType1 = vehicleType.equalsIgnoreCase("twoWheeler")
                ? VehicleType.TWO_WHEELER
                : vehicleType.equalsIgnoreCase("fourWheeler")
                ? VehicleType.FOUR_WHEELER
                : null;
        Vehicle vehicle = new Vehicle(requestDTO.getVehicleNumber(), vehicleType1);
        Ticket ticket;
        try {
            ticket = parkingLotService.parkVehicle(vehicle);
        } catch (ParkingNotAvailable e) {
            return new ResponseDTO(HttpStatus.SERVICE_UNAVAILABLE, "sorry! parking not available for : " + requestDTO.getVehicleNumber());
        }
        return new ResponseDTO(HttpStatus.ACCEPTED, "Parked succesfully... Your ticket details are : " + ticket.toString());
    }

    @GetMapping("/unpark")
    public ResponseDTO unparkVehicle(RequestDTO requestDTO) throws ParseException {
        Ticket ticket = getTicket(requestDTO);
        Long price;
        try {
            price = parkingLotService.unparkVehicle(ticket);
        } catch (ParkingNotAvailable | VehicleNotFound e) {
            return new ResponseDTO(HttpStatus.ACCEPTED, "Sorry your vehicle isn't present in the system. Please pay : " + Constants.MINIMUM_AMOUNT_TO_PAY);
        }
        return new ResponseDTO(HttpStatus.ACCEPTED, "Unparked succesfully... Please pay : " + price);
    }

    private Ticket getTicket(RequestDTO requestDTO) throws ParseException {
        String vehicleType = requestDTO.getVehicleType();
        VehicleType vehicleType1 = vehicleType.equalsIgnoreCase("twoWheeler")
                ? VehicleType.TWO_WHEELER
                : vehicleType.equalsIgnoreCase("fourWheeler")
                ? VehicleType.FOUR_WHEELER
                : null;
        Vehicle vehicle = new Vehicle(requestDTO.getVehicleNumber(), vehicleType1);
        String date = requestDTO.getDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date finalDate = formatter.parse(date);

        return new Ticket(requestDTO.getSlotNumber(), vehicle, finalDate, vehicleType1);
    }

}
