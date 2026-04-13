package com.pm.patientservice1.controller;

import com.pm.patientservice1.dto.PatientRequestDTO;
import com.pm.patientservice1.dto.PatientResponseDTO;
import com.pm.patientservice1.dto.validators.CreatePatientValidationGroup;
import com.pm.patientservice1.kafka.KafkaProducer;
import com.pm.patientservice1.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "patient", description = "API for managing the Patients")
//This tag is used for automating the documentation task for swagger API, so hit the v3/api-docs
public class PatientController {
    @Autowired

    private final PatientService patientService;
    private final KafkaProducer kafkaProducer;

    public PatientController(KafkaProducer kafkaProducer, PatientService patientService) {
        this.kafkaProducer = kafkaProducer;
        this.patientService = patientService;
    }
    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patientResponseDTOS = patientService.findAll();
        return ResponseEntity.ok(patientResponseDTOS);
    }


    @PostMapping
    @Operation(summary= "Create a new Patient")
    //RequestBody here converts the JSON request to PatientRequestDTO
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({CreatePatientValidationGroup.class, Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok(patientResponseDTO);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public  ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,patientRequestDTO);
        return ResponseEntity.ok(patientResponseDTO);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return  ResponseEntity.noContent().build();
    }
//    @PostMapping("/send")
//    public String send(@RequestBody String msg) {
//        kafkaProducer.sendEvent(msg);
//        return "Message sent";
//    }
//@PostMapping("/send")
//public String send(@RequestBody String msg) {
//    patientService.sendMessage(msg);
//    return "Message sent";
//}
@GetMapping("/{id}")
@Operation(summary = "Get Patient by ID")
public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable UUID id) {
    PatientResponseDTO response = patientService.getPatientById(id);
    return ResponseEntity.ok(response);
}


}
