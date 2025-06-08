package com.spring.rapifuzz.exam.util;

import com.spring.rapifuzz.exam.repo.IncidentRepository;

import java.time.Year;
import java.util.Random;


public class IncidentIdGenerator {

    private static final Random random = new Random();

    public static String generateIncidentId(IncidentRepository incidentRepository) {
        int randomFiveDigit = 10000 + random.nextInt(90000); // Generate a 5-digit random number
        int currentYear = Year.now().getValue(); // Get the current year
        String incidentId = "RMG" + randomFiveDigit + currentYear;
        if (incidentRepository.existsById(incidentId)) {
            return generateIncidentId(incidentRepository);
        }
        return incidentId;
    }
}

