package com.pm.patientservice1.kafka;

//import com.pm.patientservice1.event.PatientEvent;
import com.pm.common.event.PatientEvent;
import com.pm.patientservice1.model.Patient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

//@Service
//public class KafkaProducer{
//    private final KafkaTemplate<String,String> kafkaTemplate;
//
//    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//    public void sendEvent(String message){
//        kafkaTemplate.send("patient-topic",message);
//        System.out.println("Sent to Kafka: " + message);
//    }
//}
//@Service
//public class KafkaProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public KafkaProducer(KafkaTemplate<String, PatientEvent> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendEvent(String message) {
//        kafkaTemplate.send("patient-topic", message);
//        System.out.println("🔥 Sent to Kafka: " + message);
//    }
//}
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, PatientEvent> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    public KafkaProducer(KafkaTemplate<String, PatientEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    public void sendEvent(PatientEvent event, String string) {
//        event.setEventId(UUID.randomUUID().toString());
//        event.setPatientId(Patient.getId());
//        event.setAction("CREATED");
//        kafkaTemplate.send("patient-topic", event);
//        System.out.println("🔥 Sent DTO: " + event.getName());
//    }
    public void sendEvent(PatientEvent event, String patientId) {
        event.setEventId(UUID.randomUUID().toString());
        event.setPatientId(patientId);
        event.setAction("CREATED");
        log.info("📤 Sent event to Kafka: {}", event);
        kafkaTemplate.send("patient-topic", event);
    }
}