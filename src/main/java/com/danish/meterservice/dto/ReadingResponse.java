package com.danish.meterservice.dto;


import com.danish.meterservice.enums.MeterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReadingResponse {
    private Long meterNumber;
    private Long readingValue;
    private Long readingId;
    private MeterType meterType;
}
