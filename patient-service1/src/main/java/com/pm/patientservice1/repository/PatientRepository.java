package com.pm.patientservice1.repository;

import com.pm.patientservice1.model.BillingStatus;
import com.pm.patientservice1.model.Patient;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);

    List<Patient> findByBillingStatus(BillingStatus billingStatus);
}
