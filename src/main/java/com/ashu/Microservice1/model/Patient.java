package com.ashu.Microservice1.model;

import java.util.List;

public class Patient {
    private int id;
    private String name;
    private String city;
    private List<Integer> serviceIds; // only stores IDs
    private List<Services> services; // populated from remote call

    public Patient() {
    }

    public Patient(int id, String name, String city, List<Integer> serviceIds) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.serviceIds = serviceIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public List<Integer> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Integer> serviceIds) {
        this.serviceIds = serviceIds;
    }
}
