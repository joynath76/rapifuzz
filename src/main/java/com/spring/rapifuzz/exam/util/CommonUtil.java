package com.spring.rapifuzz.exam.util;

import com.spring.rapifuzz.exam.dto.PostOffice;
import com.spring.rapifuzz.exam.dto.RegionResponse;

public class CommonUtil {

    public static RegionResponse postOfficeToRegionResponse(PostOffice postOffice) {
        return RegionResponse.builder()
                .city(postOffice.getRegion())
                .country(postOffice.getCountry())
                .pinCode(postOffice.getPincode())
                .state(postOffice.getState())
                .build();
    }
}
