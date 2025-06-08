package com.spring.rapifuzz.exam.entity;

import com.spring.rapifuzz.exam.validator.ValidIncidentPriority;
import com.spring.rapifuzz.exam.validator.ValidIncidentStatus;
import com.spring.rapifuzz.exam.validator.ValidIncidentType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class Incident {
    @Id
    private String incidentId;

    @NotNull
    @ValidIncidentType
    @Column(nullable = false)
    private String incidentType;

    @Column(nullable = false)
    private String reporterName;

    @NotNull
    @ValidIncidentStatus
    @Column(nullable = false)
    private String status;

    @NotNull
    @ValidIncidentPriority
    @Column(nullable = false)
    private String priority;

    @NotNull(message = "Incident title cannot be null")
    @NotEmpty(message = "Incident title cannot be empty")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Incident description cannot be null")
    @NotEmpty(message = "Incident description cannot be empty")
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long createdBy;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;


    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}

