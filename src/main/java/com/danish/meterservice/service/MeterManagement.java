package com.danish.meterservice.service;

import com.danish.meterservice.dto.MeterRequest;
import com.danish.meterservice.Repository.MeterRepository;
import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.exception.MeterAlreadyExistsException;
import com.danish.meterservice.exception.MeterNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeterManagement {
    private final MeterRepository meterRepository;
    public Meter addMeter(MeterRequest meterRequest){
        if(meterRepository.existsById(meterRequest.getMeterNumber())){
            log.warn("Meter Number already exists :{}",meterRequest.getMeterNumber());
            throw new MeterAlreadyExistsException("Meter Number already exists: "+meterRequest.getMeterNumber());
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


    public void deleteMeter(long meterNumber){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }
        meterRepository.deleteById(meterNumber);
    }

    public Meter getMeter(long meterNumber){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }
        return meterRepository.findById(meterNumber).get();
    }

    public Meter changeMeterStatus(long meterNumber, MeterStatus newStatus){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }
        Meter updatedMeter = meterRepository.findById(meterNumber).get();
        updatedMeter.setMeterStatus(newStatus);
        return meterRepository.save(updatedMeter);
    }
}
