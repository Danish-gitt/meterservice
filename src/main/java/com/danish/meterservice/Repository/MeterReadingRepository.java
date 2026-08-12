package com.danish.meterservice.Repository;

import com.danish.meterservice.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading,Long> {

    Optional<MeterReading> findTopByMeterMeterNumberOrderByReadingIdDesc(Long meterNumber);
    List<MeterReading> findTop2ByMeterMeterNumberOrderByReadingTimeDesc(Long meterNumber);
}
