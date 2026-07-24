package com.danish.meterservice.Repository;

import com.danish.meterservice.entity.Meter;
import com.danish.meterservice.enums.MeterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends JpaRepository<Meter,Long> {
    List<Meter> findByMeterStatus(MeterStatus meterStatus);
}
