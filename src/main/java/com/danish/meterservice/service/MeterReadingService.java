package com.danish.meterservice.service;

import com.danish.meterservice.Repository.MeterReadingRepository;
import com.danish.meterservice.Repository.MeterRepository;
import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.entity.MeterReading;
import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.exception.MeterNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
