package com.danish.meterservice.controller;

import com.danish.meterservice.dto.MeterRequest;
import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.service.MeterManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meter")
public class MeterController {

    private final MeterManagement meterManagement;

    @PostMapping("/add")
    public ResponseEntity<Meter> addMeter(@Valid @RequestBody MeterRequest meterRequest){
       Meter savedMeter = meterManagement.addMeter(meterRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedMeter);
    }


    @DeleteMapping("/delete/{meterNumber}")
    public ResponseEntity<Void> deleteMeter(@PathVariable long meterNumber){
        meterManagement.deleteMeter(meterNumber);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get/{meterNumber}")
    public ResponseEntity<Meter> getMeter(@PathVariable long meterNumber){
        Meter foundMeter = meterManagement.getMeter(meterNumber);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(foundMeter);
    }

    @PostMapping("/update/{meterNumber}/{newStatus}")
    public ResponseEntity<Meter> updateMeterStatus(@PathVariable long meterNumber, @PathVariable MeterStatus newStatus){
        Meter updatedMeter = meterManagement.changeMeterStatus(meterNumber,newStatus);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(updatedMeter);
    }

}
