package com.danish.meterservice.entity;

import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.enums.MeterType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="meter")
@Data
public class Meter {

    @Id
    @Column(name="meterNumber",nullable = false,unique = true)
    private long meterNumber;


    @Column(name="msisdn",nullable = false)
    private long msisdn;

    @Column(name="customerName",nullable = false)
    private String customerName;

    @Column(name="address",nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name="meterType",nullable = false)
    private MeterType meterType;

    @CreationTimestamp
    @Column(name="createdOn",nullable = false,updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name="updatedOn",nullable = false)
    private LocalDateTime updatedOn;

    @Enumerated(EnumType.STRING)
    @Column(name="meterStatus",nullable = false)
    private MeterStatus meterStatus;


}
