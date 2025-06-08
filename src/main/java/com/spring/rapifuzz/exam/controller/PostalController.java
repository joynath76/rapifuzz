package com.spring.rapifuzz.exam.controller;


import com.spring.rapifuzz.exam.dto.RegionResponse;
import com.spring.rapifuzz.exam.entity.User;
import com.spring.rapifuzz.exam.service.PinCodeService;
import com.spring.rapifuzz.exam.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/postal")
public class PostalController {

    @Autowired
    private PinCodeService pinCodeService;

    @GetMapping("/{pinCode}")
    public ResponseEntity<RegionResponse> getPostalDetails(@PathVariable Long pinCode) {
        Validator.notNull(pinCode, "PinCode");
        return ResponseEntity.ok(pinCodeService.getRegionByPinCode(pinCode));
    }

}
