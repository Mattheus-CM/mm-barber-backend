package com.martins.mm_barber.catalog.application;

public class ServiceOfferingNotFoundException extends RuntimeException {
    public ServiceOfferingNotFoundException(Long serviceId, Long barbershopId) {
        super("Service Offering with id " + serviceId + " not found in barbershop with id "
                + barbershopId);
    }
}
