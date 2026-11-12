package com.martins.mm_barber.catalog.application;

public class ServiceOfferingAlreadyExistsException extends RuntimeException {
    public ServiceOfferingAlreadyExistsException(String name) {
        super("A service named '" + name + "' already exists in this barbershop");
    }
}
