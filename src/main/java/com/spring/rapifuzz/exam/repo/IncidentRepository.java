package com.spring.rapifuzz.exam.repo;

import com.spring.rapifuzz.exam.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, String> {
    List<Incident> findIncidentByCreatedBy(Long userId);
    Optional<Incident> findIncidentByCreatedByAndIncidentId(Long userId, String incidentId);
}

