package com.spring.rapifuzz.exam.service;

import com.spring.rapifuzz.exam.entity.Incident;

import java.util.List;

public interface IncidentService {
    Incident createIncident(Incident incident);
    List<Incident> getAllIncidents();
    Incident getIncidentById(String incidentId);
    Incident updateIncident(String incidentId, Incident incidentDetails);
    void deleteIncident(String incidentId);
}

