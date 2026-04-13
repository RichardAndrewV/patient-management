package com.pm.patientservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableCaching
@SpringBootApplication
@EnableScheduling
public class PatientService1Application {

    public static void main(String[] args) {
        SpringApplication.run(PatientService1Application.class, args);
    }

}
