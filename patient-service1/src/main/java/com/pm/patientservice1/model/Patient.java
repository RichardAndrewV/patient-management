package com.pm.patientservice1.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import jakarta.persistence.Column;

@Entity
@Table(name = "patient")
public class Patient {
    //we use private so it cannot be accessed directly it can be accessed by getters and setters only for achieving ENCAPSULATION
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //UUID = Universally Unique Identifier
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;


    @NotNull
    private String address;


    @NotNull
    private LocalDate dateOfBirth;


    @NotNull
    private LocalDate registeredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_status")
    private BillingStatus billingStatus;

    @Column(name = "retry_count")
    private int retryCount;
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }
    public BillingStatus getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(BillingStatus billingStatus) {
        this.billingStatus = billingStatus;
    }
    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}

