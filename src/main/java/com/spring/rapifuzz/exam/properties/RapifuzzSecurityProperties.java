package com.spring.rapifuzz.exam.properties;

import com.spring.rapifuzz.exam.dto.SecurityDto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "rapifuzz.security")
@Data
public class RapifuzzSecurityProperties {
    private List<SecurityDto> roles;
    private List<String> permitUrls;
}

