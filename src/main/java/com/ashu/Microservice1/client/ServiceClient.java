package com.ashu.Microservice1.client;

import com.ashu.Microservice1.model.Services;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Service
@FeignClient(name = "service-service")
public interface ServiceClient {

    @GetMapping("/services/{id}")
    Services getById(@PathVariable("id") int id);
}
