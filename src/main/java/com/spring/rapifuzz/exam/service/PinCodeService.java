package com.spring.rapifuzz.exam.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.rapifuzz.exam.dto.RegionResponse;

public interface PinCodeService {
    RegionResponse getRegionByPinCode(Long pinCode);
}
