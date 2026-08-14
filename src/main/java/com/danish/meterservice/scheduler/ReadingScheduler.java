package com.danish.meterservice.scheduler;

import com.danish.meterservice.service.MeterReadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReadingScheduler {
    private final MeterReadingService meterReadingService;

    @Scheduled(fixedRate = 60000)
    public void generateMeterReading(){
        log.info("Meter Reading Scheduler started");
        meterReadingService.generateReading();
        log.info("Meter Reading Scheduler completed");
    }
}
