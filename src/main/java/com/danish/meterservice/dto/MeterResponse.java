package com.danish.meterservice.dto;

import com.danish.meterservice.enums.MeterStatus;
import com.danish.meterservice.enums.MeterType;
import lombok.Data;

@Data
public class MeterResponse {
    private Long meterNumber;
    private Long msisdn;
    private String customerName;
    private String address;
    private MeterType meterType;
    private MeterStatus meterStatus;
}
