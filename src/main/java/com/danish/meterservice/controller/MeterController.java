package com.danish.meterservice.controller;

import com.danish.meterservice.dto.MeterRequest;
import com.danish.meterservice.dto.MeterResponse;
import com.danish.meterservice.dto.ReadingResponse;
import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.entity.MeterReading;
import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.service.MeterManagement;
import com.danish.meterservice.service.MeterReadingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meter")
public class MeterController {

    private final MeterManagement meterManagement;
    private final MeterReadingService meterReadingService;

    @PostMapping("/add")
    public ResponseEntity<MeterResponse> addMeter(@Valid @RequestBody MeterRequest meterRequest){
       Meter savedMeter = meterManagement.addMeter(meterRequest);
       MeterResponse response = new MeterResponse();
       response.setMeterNumber(savedMeter.getMeterNumber());
       response.setMsisdn(savedMeter.getMsisdn());
       response.setCustomerName(savedMeter.getCustomerName());
       response.setAddress(savedMeter.getAddress());
       response.setMeterType(savedMeter.getMeterType());
       response.setMeterStatus(savedMeter.getMeterStatus());
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/delete/{meterNumber}")
    public ResponseEntity<Void> deleteMeter(@PathVariable long meterNumber){
        meterManagement.deleteMeter(meterNumber);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get/{meterNumber}")
    public ResponseEntity<MeterResponse> getMeter(@PathVariable long meterNumber){
        Meter foundMeter = meterManagement.getMeter(meterNumber);
        MeterResponse response = new MeterResponse();
        response.setMeterNumber(foundMeter.getMeterNumber());
        response.setMsisdn(foundMeter.getMsisdn());
        response.setCustomerName(foundMeter.getCustomerName());
        response.setAddress(foundMeter.getAddress());
        response.setMeterType(foundMeter.getMeterType());
        response.setMeterStatus(foundMeter.getMeterStatus());
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(response);
    }

    @PostMapping("/update/{meterNumber}/{newStatus}")
    public ResponseEntity<MeterResponse> updateMeterStatus(@PathVariable Long meterNumber, @PathVariable MeterStatus newStatus){
        Meter updatedMeter = meterManagement.changeMeterStatus(meterNumber,newStatus);
        MeterResponse response = new MeterResponse();
        response.setMeterNumber(updatedMeter.getMeterNumber());
        response.setMsisdn(updatedMeter.getMsisdn());
        response.setCustomerName(updatedMeter.getCustomerName());
        response.setAddress(updatedMeter.getAddress());
        response.setMeterType(updatedMeter.getMeterType());
        response.setMeterStatus(updatedMeter.getMeterStatus());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/getStatus/{meterNumber}")
    public ResponseEntity<MeterStatus> getMeterStatus(@PathVariable Long meterNumber){
        return ResponseEntity.status(HttpStatus.FOUND).body(meterManagement.getMeterStatus(meterNumber));
    }

    @GetMapping("/getReadings/{meterNumber}")
    public ResponseEntity<List<ReadingResponse>> getReadings(@PathVariable Long meterNumber){
        return ResponseEntity.status(HttpStatus.FOUND).body(meterReadingService.getTwoReadings(meterNumber));
    }


    @GetMapping("/getActiveMeters")
    public ResponseEntity<List<MeterResponse>> getActiveMeters(){
        return ResponseEntity.status(HttpStatus.FOUND).body(meterManagement.getActiveMeters());
    }

}
