package com.spring.rapifuzz.exam.controller;

import com.spring.rapifuzz.exam.entity.Incident;
import com.spring.rapifuzz.exam.service.IncidentService;
import com.spring.rapifuzz.exam.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentService incidentService; // Service layer to handle business logic

    // Create a new incident
    @PostMapping
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody Incident incident) {
        Incident createdIncident = incidentService.createIncident(incident);
        return new ResponseEntity<>(createdIncident, HttpStatus.CREATED);
    }

    // Get all incidents
    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentService.getAllIncidents();
        return new ResponseEntity<>(incidents, HttpStatus.OK);
    }

    // Get a specific incident by ID
    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable("id") String id) {
        Validator.notNull(id, "incidentId");
        Incident incident = incidentService.getIncidentById(id);
        return new ResponseEntity<>(incident, HttpStatus.OK);
    }

    // Update an existing incident by ID
    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable("id") String id,@Valid @RequestBody Incident incidentDetails) {
        Validator.notNull(id, "incidentId");
        Incident updatedIncident = incidentService.updateIncident(id, incidentDetails);
        return new ResponseEntity<>(updatedIncident, HttpStatus.OK);
    }

    // Delete an incident by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable("id") String id) {
        Validator.notNull(id, "incidentId");
        incidentService.deleteIncident(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }
}

