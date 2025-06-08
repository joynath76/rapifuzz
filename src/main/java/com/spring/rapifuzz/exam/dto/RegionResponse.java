package com.spring.rapifuzz.exam.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegionResponse {
    private String city;
    private String country;
    private String pinCode;
    private String state;
}
