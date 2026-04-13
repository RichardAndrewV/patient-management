package com.pm.billingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "failed_events")
public class FailedEvent {

    @Id
    private String eventId;

    private String patientId;
    private String action;
    private LocalDateTime failedAt = LocalDateTime.now();

    public FailedEvent() {}

    public FailedEvent(String eventId, String patientId, String action) {
        this.eventId = eventId;
        this.patientId = patientId;
        this.action = action;
        this.failedAt = LocalDateTime.now();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(LocalDateTime failedAt) {
        this.failedAt = failedAt;
    }

}