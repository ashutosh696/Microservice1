package com.ashu.Microservice1.controller;

import com.ashu.Microservice1.model.Patient;
import com.ashu.Microservice1.model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private RestTemplate restTemplate;

    private static final List<Patient> patients = List.of(
            new Patient(1, "Ashu", "Delhi", List.of(1, 2)),
            new Patient(2, "Neha", "New Delhi", List.of(1))
    );

    @GetMapping
    public List<Patient> getAllPatients() {
        for (Patient p : patients) {
            List<Services> services = p.getServiceIds().stream()
                    .map(id -> restTemplate.getForObject("http://localhost:8082/services/" + id, Services.class))
                    .toList();
            p.setServices(services);
        }
        return patients;
    }
}
