package com.danish.meterservice.service;

import com.danish.meterservice.Repository.MeterReadingRepository;
import com.danish.meterservice.dto.MeterRequest;
import com.danish.meterservice.Repository.MeterRepository;
import com.danish.meterservice.dto.MeterResponse;
import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.entity.MeterReading;
import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.exception.MeterAlreadyExistsException;
import com.danish.meterservice.exception.MeterNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeterManagement {
    private final MeterRepository meterRepository;
    private final MeterReadingService meterReadingService;
    private final MeterReadingRepository meterReadingRepository;

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
        meterRepository.save(newMeter);
        log.info("New meter added successfully :{}",newMeter);

        //Adding a zero meter Reading
        MeterReading zeroReading = new MeterReading();
        zeroReading.setMeter(newMeter);
        zeroReading.setReadingValue(0L);
        meterReadingRepository.save(zeroReading);
        log.info("Zero Reading added for meter: {}",newMeter);


        return newMeter;
    }


    public void deleteMeter(long meterNumber){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }
        meterRepository.deleteById(meterNumber);
        log.info("Meter deleted successfully :{}",meterNumber);
    }

    public Meter getMeter(long meterNumber){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }
        Meter foundMeter = meterRepository.findById(meterNumber).get();
        log.info("Meter found :{}",meterNumber);
        return foundMeter;
    }

    public Meter changeMeterStatus(long meterNumber, MeterStatus newStatus){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }
        Meter updatedMeter = meterRepository.findById(meterNumber).get();
        updatedMeter.setMeterStatus(newStatus);
        meterRepository.save(updatedMeter);
        log.info("Meter {} status updated successfully to :{}",meterNumber,newStatus);
        return updatedMeter;
    }

    public MeterStatus getMeterStatus(Long meterNumber){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }
        Meter foundMeter = meterRepository.findById(meterNumber).get();
        return foundMeter.getMeterStatus();
    }


    public List<MeterResponse> getActiveMeters(){
        List<Meter> activeMeters = meterRepository.findByMeterStatus(MeterStatus.ACTIVE);
        List<MeterResponse> responses = new ArrayList<>();
       // MeterResponse singleResponse =  new MeterResponse();
        for(Meter singleMeter : activeMeters){
            MeterResponse singleResponse =  new MeterResponse();
            singleResponse.setMeterNumber(singleMeter.getMeterNumber());
            singleResponse.setMsisdn(singleMeter.getMsisdn());
            singleResponse.setCustomerName(singleMeter.getCustomerName());
            singleResponse.setAddress(singleMeter.getAddress());
            singleResponse.setMeterType(singleMeter.getMeterType());
            singleResponse.setMeterStatus(singleMeter.getMeterStatus());
            responses.add(singleResponse);
        }
        return responses;
    }
}
