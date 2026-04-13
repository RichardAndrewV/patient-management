package com.pm.billingservice.kafka;

import com.pm.billingservice.entity.FailedEvent;
import com.pm.billingservice.entity.ProcessedEvent;
import com.pm.billingservice.repository.FailedEventRepository;
import com.pm.billingservice.repository.ProcessedEventRepository;
import com.pm.common.event.PatientEvent;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
@Service
public class PatientEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(PatientEventConsumer.class);

    private final FailedEventRepository failedEventRepository;
    private final ProcessedEventRepository processedRepo;

    public PatientEventConsumer(FailedEventRepository failedEventRepository,
                                ProcessedEventRepository processedRepo) {
        this.failedEventRepository = failedEventRepository;
        this.processedRepo = processedRepo;
    }

    @PostConstruct
    public void init() {
        log.info("🔥 Consumer bean initialized");
    }
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 5000), // 5 sec delay
            dltTopicSuffix = "-dlq"
    )
    @KafkaListener(
            topics = "patient-topic",
            groupId = "billing-group-new-999",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PatientEvent event) {

        String eventId = event.getEventId();
        String email=event.getEmail();
        try {
            // 🔥 STEP 1: Idempotency guard (DB constraint)
            processedRepo.save(new ProcessedEvent(eventId));

            // 🔥 STEP 2: Process
            log.info("Processing event: {}", eventId);
            processBilling(eventId,email);

        } catch (Exception e) {

            // 🔥 STEP 3: Duplicate → ignore
            if (e.getMessage() != null &&
                    (e.getMessage().toLowerCase().contains("constraint")
                            || e.getMessage().toLowerCase().contains("duplicate"))) {

                log.warn("Duplicate skipped: {}", eventId);
                return;
            }

            // 🔥 STEP 4: Real failure
            log.error("Error processing event: {}", eventId, e);
            throw e;
        }
    }

    private void processBilling(String eventId,String email) {
        if (email.contains("fail")) {
            throw new RuntimeException("Simulated failure");
        }


        log.info("Billing done for: {}", eventId);
    }

    // ✅ DLQ HANDLER (FIXED)
    @KafkaListener(topics = "patient-topic-dlq")
    public void consumeDLQ(PatientEvent event) {

        log.error("DLQ Event: {}", event);

        // ✅ FIX: use injected repository
        failedEventRepository.save(new FailedEvent(
                event.getEventId(),
                event.getPatientId(),
                event.getAction()
        ));
    }
}