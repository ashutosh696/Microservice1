package com.ashu.Microservice1.controller;

import com.ashu.Microservice1.client.ServiceClient;
import com.ashu.Microservice1.model.Patient;
import com.ashu.Microservice1.model.Services;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
     private ServiceClient serviceClient;
    @Autowired
    private RestTemplate restTemplate;

    private static final List<Patient> patients = List.of(
            new Patient(1, "Ashu", "Delhi", List.of(1, 2)),
            new Patient(2, "Neha", "New Delhi", List.of(1))
    );

    @GetMapping
   // @CircuitBreaker(name="serviceServiceBreaker", fallbackMethod = "fallbackMethod")
    @Retry(name="serviceRetry", fallbackMethod = "fallbackMethodRetry")
    public List<Patient> getAllPatients() {
        System.out.println("Patient MS");
        for (Patient p : patients) {
            List<Services> services = p.getServiceIds().stream()
                    .map(id -> restTemplate.getForObject("http://service-service/services/" + id, Services.class))
                    .toList();
            p.setServices(services);
        }
        return patients;
    }

    /*public List<Patient> fallbackMethod(Throwable t) {
        System.out.println("fallbackMethod"+t.getMessage());

        for (Patient p : patients) {

          //  p.setServices(Collections.emptyList());
            p.setServices(List.of(new Services("Service is not available now, Please try after sometime!!")));
        }
        return patients;

    }*/
    public List<Patient> fallbackMethodRetry(Throwable t) {
        System.out.println("fallbackMethod Retry: "+t.getMessage());

        for (Patient p : patients) {

            //  p.setServices(Collections.emptyList());
            p.setServices(List.of(new Services("Service is not available now, Please try after sometime!!")));
        }
        return patients;

    }

    @GetMapping("/getAllFeign")
    public List<Patient> getAllFeign()  {
        System.out.println("Patient MS");

        for (Patient p : patients) {
            List<Services> services = p.getServiceIds().stream()
                    .map(serviceClient::getById)
                    .toList();
            p.setServices(services);
        }

        return patients;
    }
   

}
