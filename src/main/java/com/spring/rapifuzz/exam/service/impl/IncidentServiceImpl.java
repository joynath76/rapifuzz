package com.spring.rapifuzz.exam.service.impl;

import com.spring.rapifuzz.exam.entity.Incident;
import com.spring.rapifuzz.exam.exception.ResourceNotFoundException;
import com.spring.rapifuzz.exam.exception.UnauthorizedException;
import com.spring.rapifuzz.exam.repo.IncidentRepository;
import com.spring.rapifuzz.exam.service.AbstractService;
import com.spring.rapifuzz.exam.service.IncidentService;
import com.spring.rapifuzz.exam.util.IncidentIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IncidentServiceImpl extends AbstractService implements IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public Incident createIncident(Incident incident) {
        incident.setIncidentId(IncidentIdGenerator.generateIncidentId(incidentRepository));
        incident.setCreatedBy(1L);
        incident.setReporterName("admin");
        kafkaTemplate.send("kafka-topic", incident);
        return incident;
    }

    @KafkaListener(groupId = "rapifuzz-group", topics = "kafka-topic")
    private void saveIncident(Incident incident) {
        log.error("Consumed incident: {}", incident);
        incidentRepository.save(incident);
    }

    @Override
    public List<Incident> getAllIncidents() {
        return incidentRepository.findIncidentByCreatedBy(getLoggedInUserId());
    }

    @Override
    public Incident getIncidentById(String incidentId) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with ID: " + incidentId));;
        if (!incident.getCreatedBy().equals(getLoggedInUserId())) {
            throw new UnauthorizedException("Unauthorized to view incident with ID: " + incidentId);
        }
        return incident;
    }

    @Override
    public Incident updateIncident(String incidentId, Incident incidentDetails) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with ID: " + incidentId));;
        if (!incident.getCreatedBy().equals(getLoggedInUserId())) {
            throw new UnauthorizedException("Unauthorized to update incident with ID: " + incidentId);
        }
        incident.setIncidentType(incidentDetails.getIncidentType());
        incident.setReporterName(incidentDetails.getReporterName());
        incident.setStatus(incidentDetails.getStatus());
        incident.setPriority(incidentDetails.getPriority());
        incident.setDescription(incidentDetails.getDescription());
        incident.setTitle(incidentDetails.getTitle());
        incident.setReporterName(getLoggedInUsername());
        return incidentRepository.save(incident);
    }

    @Override
    public void deleteIncident(String incidentId) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with ID: " + incidentId));;
        if (!incident.getCreatedBy().equals(getLoggedInUserId())) {
            throw new UnauthorizedException("Unauthorized to delete incident with ID: " + incidentId);
        }
        incidentRepository.deleteById(incidentId);
    }
}

