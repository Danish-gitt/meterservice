package com.danish.meterservice.controller;

import com.danish.meterservice.dto.MeterRequest;
import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.service.MeterManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
