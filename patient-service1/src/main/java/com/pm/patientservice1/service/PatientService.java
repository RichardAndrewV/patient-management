//package com.pm.patientservice1.service;
//
//import billing.BillingResponse;
//import com.pm.common.event.PatientEvent;
//import com.pm.patientservice1.dto.PatientRequestDTO;
//import com.pm.patientservice1.dto.PatientResponseDTO;
//
//import com.pm.patientservice1.exception.EmailAlreadyExistsException;
//import com.pm.patientservice1.exception.PatientNotFoundException;
//import com.pm.patientservice1.grpc.BillingServiceGrpcClient;
//import com.pm.patientservice1.kafka.KafkaProducer;
//import com.pm.patientservice1.mapper.PatientMapper;
//import com.pm.patientservice1.model.BillingStatus;
//import com.pm.patientservice1.model.Patient;
//import com.pm.patientservice1.repository.PatientRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class PatientService {
//    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
//    @Autowired
//    private final PatientRepository patientRepository;
//    private final BillingServiceGrpcClient billingServiceGrpcClient;
//    private final KafkaProducer kafkaProducer;
//    private final PatientMapper patientMapper;
//
//
//
//    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer, PatientMapper patientMapper) {
//        this.patientRepository = patientRepository;
//        this.billingServiceGrpcClient = billingServiceGrpcClient;
//        this.kafkaProducer=kafkaProducer;
//
//        this.patientMapper = patientMapper;
//    }
//
//    public List<PatientResponseDTO> findAll() {
//        List<Patient> patients= patientRepository.findAll();
//        List<PatientResponseDTO> patientResponseDTOS = patients.stream()
//                //.map(Patient->PatientMapper.toDTO(Patient)).toList();  stream.map is like a for loop iterating through the patients list
//                .map(PatientMapper::toDTO).toList();
//        return patientResponseDTOS;
//
//    }
//    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
//        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
//            throw new EmailAlreadyExistsException("patient with this email " + patientRequestDTO.getEmail() + " already exists");
//        }
//        Patient newPatient = PatientMapper.toModel(patientRequestDTO);
//        newPatient.setBillingStatus(BillingStatus.PENDING);
//        newPatient.setRetryCount(0);
//        newPatient=patientRepository.save(newPatient);
//
//        try {
//            BillingResponse response=billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());
//            if(("SUCCESS".equals(response.getStatus()))){
//                newPatient.setBillingStatus(BillingStatus.SUCCESS);
//            }
//            else{
//                newPatient.setBillingStatus(BillingStatus.FAILED);
//            }
//
//        } catch (Exception e){
//            newPatient.setBillingStatus(BillingStatus.FAILED);
////            System.out.println("Billing Failed " + e.getMessage());
//            log.error("Billing failed", e);
//        }
//        newPatient = patientRepository.save(newPatient);
//        PatientEvent event = new PatientEvent();
//        event.setName(newPatient.getName());
//        event.setEmail(newPatient.getEmail());
//
////        kafkaProducer.sendEvent(event);
////        kafkaProducer.sendEvent(event, patient.getId());
//        kafkaProducer.sendEvent(event, newPatient.getId().toString());
////        kafkaProducer.sendEvent(event, newPatient.getId().toString());
////        kafkaProducer.sendEvent("Patient Created "+newPatient.getId());
//        return PatientMapper.toDTO(newPatient);
//
//
//
//    }
//    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
//        Patient patient = patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("Patient not found with ID: ",id));
//        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)) {
//            throw new EmailAlreadyExistsException("Patient with this email " + patientRequestDTO.getEmail() + " already exists");
//        }
//        patient.setName(patientRequestDTO.getName());
//        patient.setEmail(patientRequestDTO.getEmail());
//        patient.setAddress(patientRequestDTO.getAddress());
//        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
//        Patient updatedPatient= patientRepository.save(patient);
//        return PatientMapper.toDTO(updatedPatient);
//    }
//    public void deletePatient(UUID id) {
//        patientRepository.deleteById(id);
//    }
////    @Scheduled(fixedRate = 30000) // every 30 seconds
////    public void retryFailedBilling() {
////
////        List<Patient> failedPatients = patientRepository.findByBillingStatus(BillingStatus.FAILED);
////
////        for (Patient patient : failedPatients) {
////            if (patient.getRetryCount() >= 3) {
////                System.out.println("Max retries reached for patient: " + patient.getId());
////                continue;
////            }
////
////            try {
////                BillingResponse response = billingServiceGrpcClient.createBillingAccount(
////                        patient.getId().toString(),
////                        patient.getName(),
////                        patient.getEmail()
////                );
////
////                if ("SUCCESS".equals(response.getStatus())) {
////                    patient.setBillingStatus(BillingStatus.SUCCESS);
////                    patientRepository.save(patient);
////                    System.out.println("Retry success for patient: " + patient.getId());
////                }
////                else{
////                    patient.setRetryCount(patient.getRetryCount() + 1);
////                    patientRepository.save(patient);
////                    System.out.println("Retry failed for patient: " + patient.getId());
////                }
////
////            } catch (Exception e) {
////                System.out.println("Retry failed again for patient: " + patient.getId());
////                patient.setRetryCount(patient.getRetryCount() + 1);
////                patientRepository.save(patient);
////            }
////        }
////    }
//
////    public void sendMessage(String msg) {
////        PatientEvent event = new PatientEvent();
////        event.setName(msg);
////        event.setEmail("test@gmail.com"); // temp
////
////        kafkaProducer.sendEvent(event);
////    }
////@Cacheable(value = "patients", key = "#id")
////public PatientResponseDTO getPatientById(UUID id) {
////
////    log.info("Fetching from DB...");
////
////    Patient patient = patientRepository.findById(id)
////            .orElseThrow(() -> new RuntimeException("Patient not found"));
////
////    return patientMapper.toDTO(patient);
////}
//@Cacheable(value = "patients", key = "#id")
//public PatientResponseDTO getPatientById(UUID id) {
//
//    log.info("Fetching from DB...");
//
//    Patient patient = patientRepository.findById(id)
//            .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
//
//    return patientMapper.toDTO(patient);
//}
//    @CachePut(value = "patients", key = "#id")
//    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO dto) {
//
//        Patient patient = patientRepository.findById(id)
//                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
//
//        patient.setName(dto.getName());
//        patient.setEmail(dto.getEmail());
//        patient.setAddress(dto.getAddress());
//        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
//
//        Patient updated = patientRepository.save(patient);
//
//        return patientMapper.toDTO(updated);
//    }
//    @CacheEvict(value = "patients", key = "#id")
//    public Patient updatePatient(String id, Patient updated) {
//        return patientRepository.save(updated);
//    }
//    @CacheEvict(value = "patients", key = "#id")
//    public void deletePatient(String id) {
//        patientRepository.deleteById(UUID.fromString(id));
//    }
//}
package com.pm.patientservice1.service;

import billing.BillingResponse;
import com.pm.common.event.PatientEvent;
import com.pm.patientservice1.dto.PatientRequestDTO;
import com.pm.patientservice1.dto.PatientResponseDTO;

import com.pm.patientservice1.exception.EmailAlreadyExistsException;
import com.pm.patientservice1.exception.PatientNotFoundException;
import com.pm.patientservice1.grpc.BillingServiceGrpcClient;
import com.pm.patientservice1.kafka.KafkaProducer;
import com.pm.patientservice1.mapper.PatientMapper;
import com.pm.patientservice1.model.BillingStatus;
import com.pm.patientservice1.model.Patient;
import com.pm.patientservice1.repository.PatientRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private final PatientRepository patientRepository;

    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;
    private final PatientMapper patientMapper;

    public PatientService(
            PatientRepository patientRepository,
            BillingServiceGrpcClient billingServiceGrpcClient,
            KafkaProducer kafkaProducer,
            PatientMapper patientMapper
    ) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
        this.patientMapper = patientMapper;
    }

    // 🔹 GET ALL
    public List<PatientResponseDTO> findAll() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    // 🔹 CREATE
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "patient with this email " + patientRequestDTO.getEmail() + " already exists"
            );
        }

        Patient newPatient = PatientMapper.toModel(patientRequestDTO);
        newPatient.setBillingStatus(BillingStatus.PENDING);
        newPatient.setRetryCount(0);

        newPatient = patientRepository.save(newPatient);

        try {
            BillingResponse response = billingServiceGrpcClient.createBillingAccount(
                    newPatient.getId().toString(),
                    newPatient.getName(),
                    newPatient.getEmail()
            );

            if ("SUCCESS".equals(response.getStatus())) {
                newPatient.setBillingStatus(BillingStatus.SUCCESS);
            } else {
                newPatient.setBillingStatus(BillingStatus.FAILED);
            }

        } catch (Exception e) {
            newPatient.setBillingStatus(BillingStatus.FAILED);
            log.error("Billing failed", e);
        }

        newPatient = patientRepository.save(newPatient);

        PatientEvent event = new PatientEvent();
        event.setName(newPatient.getName());
        event.setEmail(newPatient.getEmail());

        kafkaProducer.sendEvent(event, newPatient.getId().toString());

        return PatientMapper.toDTO(newPatient);
    }

    // 🔹 GET BY ID (CACHE)
    @Cacheable(value = "patients", key = "#id")
    public PatientResponseDTO getPatientById(UUID id) {

        log.info("Fetching from DB...");

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        return patientMapper.toDTO(patient);
    }

    // 🔹 UPDATE (CACHE UPDATE)
    @CachePut(value = "patients", key = "#id")
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException(
                    "Patient with this email " + patientRequestDTO.getEmail() + " already exists"
            );
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.toDTO(updatedPatient);
    }

    // 🔹 DELETE (CACHE EVICT)
    @CacheEvict(value = "patients", key = "#id")
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
