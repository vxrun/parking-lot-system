package com.vxrun.parkinglot.controller;

import com.vxrun.parkinglot.service.ParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/initialize")
public class InitializeController {

    @Autowired
    ParkingLotService parkingLotService;

    @GetMapping("/newParkingLot")
    public String initNewParkingLot(@RequestParam("twoWheelerSize") Integer twoWheelerSize, @RequestParam("fourWheelerSize") Integer fourWheelerSize) {
        log.info("Initializing request processing for [{}: 2-Wheeler, {}: 4-Wheeler]", twoWheelerSize, fourWheelerSize);
        parkingLotService.initializeParkingLot(10, 10);
        return "new parking lot initialized..";
    }
}
