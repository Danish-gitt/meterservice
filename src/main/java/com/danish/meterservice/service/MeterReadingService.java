package com.danish.meterservice.service;

import com.danish.meterservice.Repository.MeterReadingRepository;
import com.danish.meterservice.Repository.MeterRepository;
import com.danish.meterservice.dto.ReadingResponse;
import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.entity.MeterReading;
import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.exception.MeterNotFoundException;
import com.danish.meterservice.exception.ReadingNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeterReadingService {
    private final MeterRepository meterRepository;
    private final MeterReadingRepository meterReadingRepository;
    private final Random random = new Random();

    public void generateReading() {
        List<Meter> activeMeters = meterRepository.findByMeterStatus(MeterStatus.ACTIVE);
        if (activeMeters == null || activeMeters.isEmpty()) {
            log.warn("Active Meter List is empty. Skipping reading generation!!");
            return;
        }
        for (Meter meter : activeMeters) {
            Optional<MeterReading> latestReading = meterReadingRepository.
                    findTopByMeterMeterNumberOrderByReadingIdDesc(meter.getMeterNumber());
           long previousReading;
            if(latestReading.isPresent()){
                previousReading=latestReading.get().getReadingValue();
            }else{
                previousReading=0;
            }
            int randomValue = random.nextInt(10);
            long currentReading=previousReading+randomValue;
            MeterReading newReading = new MeterReading();
            newReading.setReadingValue(currentReading);
            newReading.setMeter(meter);
            meterReadingRepository.save(newReading);
        }
    }


    public List<ReadingResponse> getTwoReadings(Long meterNumber){
        if(!meterRepository.existsById(meterNumber)){
            log.warn("Meter Number does not exists :{}",meterNumber);
            throw new MeterNotFoundException("Meter Number does not exists: "+meterNumber);
        }

        List<MeterReading> foundReadings = meterReadingRepository.findTop2ByMeterMeterNumberOrderByReadingTimeDesc(meterNumber);
        if(foundReadings.size()<2){
            log.warn("Insufficient readings for meter Number: {}",meterNumber);
            throw new ReadingNotFoundException("Insufficient readings for meter Number: "+meterNumber);
        }


        List<ReadingResponse> responses = new ArrayList<>();
        for(MeterReading meterReading : foundReadings){
            //ReadingResponse res = new ReadingResponse(meterReading.getMeter().getMeterNumber(),meterReading.getReadingValue());
            ReadingResponse res = new ReadingResponse();
            res.setMeterNumber(meterReading.getMeter().getMeterNumber());
            res.setReadingId(meterReading.getReadingId());
            res.setReadingValue(meterReading.getReadingValue());
            res.setMeterType(meterReading.getMeter().getMeterType());
            responses.add(res);
        }
        return responses;


    }



}
