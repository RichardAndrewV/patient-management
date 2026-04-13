package com.pm.common.event;

public class PatientEvent {

        private String patientId;
        private String name;
        private String email;
    private String eventId;

    private String action;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PatientEvent() {}

        public PatientEvent(String patientId, String name, String email) {
            this.patientId = patientId;
            this.name = name;
            this.email = email;
        }

        public String getPatientId() { return patientId; }
        public void setPatientId(String patientId) { this.patientId = patientId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }


