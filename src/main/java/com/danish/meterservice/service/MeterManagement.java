package com.danish.meterservice.service;

import com.danish.meterservice.dto.MeterRequest;
import com.danish.meterservice.Repository.MeterRepository;
import com.danish.meterservice.entity.Meter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeterManagement {
    private final MeterRepository meterRepository;
    public Meter addMeter(MeterRequest meterRequest){
        if(meterRepository.existsById(meterRequest.getMeterNumber())){
            log.warn("Meter Number already exists :{}",meterRequest.getMeterNumber());
            throw new RuntimeException("Meter Number already exists");

        }

        Meter newMeter = new Meter();
        newMeter.setMeterNumber(meterRequest.getMeterNumber());
        newMeter.setMsisdn(meterRequest.getMsisdn());
        newMeter.setCustomerName(meterRequest.getCustomerName());
        newMeter.setAddress(meterRequest.getAddress());
        newMeter.setMeterType(meterRequest.getMeterType());
        newMeter.setMeterStatus(meterRequest.getMeterStatus());
        return meterRepository.save(newMeter);
    }
}
