package com.spring.rapifuzz.exam.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rapifuzz.exam.dto.PincodeResponse;
import com.spring.rapifuzz.exam.dto.RegionResponse;
import com.spring.rapifuzz.exam.exception.ResourceNotFoundException;
import com.spring.rapifuzz.exam.service.PinCodeService;
import com.spring.rapifuzz.exam.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PinCodeServiceImpl implements PinCodeService {

    @Autowired
    private WebClient webClient;

    @Value("${api.portal.pincode.base.url}")
    private String postalPinCodeBaseUrl;

    @Override
    public RegionResponse getRegionByPinCode(Long pinCode) {
        List<PincodeResponse> apiResponse = webClient.get()
                .uri(postalPinCodeBaseUrl + "/pincode/" + pinCode)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PincodeResponse>>() {})
                .block();
        PincodeResponse pincodeResponse = apiResponse.stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("Invalid pin code: " + pinCode));
        if (!"Success".equals(pincodeResponse.getStatus())) {
            throw new ResourceNotFoundException("Invalid pin code: " + pinCode);
        }
        return pincodeResponse.getPostOffices()
                .stream()
                .map(CommonUtil::postOfficeToRegionResponse)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Invalid pin code: " + pinCode));
    }
}
