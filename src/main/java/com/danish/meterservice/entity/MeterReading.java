package com.danish.meterservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="meter_reading")
@Data
public class MeterReading {
    @Id
    @Column(name="readingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readingId;

    @ManyToOne
    @JoinColumn(name="meterNumber")
    private Meter meter;

    @Column(name="readingValue",nullable = false)
    private Long readingValue;

    @CreationTimestamp
    @Column(name="readingTime",nullable = false,updatable = false)
    private LocalDateTime readingTime;


}
