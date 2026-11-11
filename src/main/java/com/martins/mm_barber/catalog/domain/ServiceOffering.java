package com.martins.mm_barber.catalog.domain;

import com.martins.mm_barber.shared.DomainException;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class ServiceOffering {
    private final Long id;
    private final Long barbershopId;
    private String name;
    private String description;
    private BigDecimal defaultPrice;
    private boolean active;

    private ServiceOffering(Long id, Long barbershopId, String name, String description,
                            BigDecimal defaultPrice, boolean active) {
        this.id = id;
        this.barbershopId = barbershopId;
        this.name = name;
        this.description = description;
        this.defaultPrice = defaultPrice;
        this.active = active;
    }

    public static ServiceOffering create(Long barbershopId, String name, String description,
                                         BigDecimal defaultPrice) {
        if  (barbershopId == null) {
            throw new DomainException("The barbershop of the service is required");
        }
        return new ServiceOffering(null, barbershopId, validateName(name), description,
                validatePrice(defaultPrice), true);
    }

    public static ServiceOffering reconstitute(Long id, Long barbershopId, String name,
                                               String description, BigDecimal defaultPrice,
                                               boolean active) {
        return new ServiceOffering(id, barbershopId, name, description, defaultPrice, active);
    }

    public void update(String name, String description, BigDecimal defaultPrice) {
        this.name = validateName(name);
        this.description = description;
        this.defaultPrice = validatePrice(defaultPrice);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("The service name is required");
        }
        return name.trim();
    }

    private static BigDecimal validatePrice(BigDecimal price) {
        if (price == null) {
            throw new DomainException("The service price cannot be null");
        }
        if (price.signum() < 0) {
            throw new DomainException("The service price cannot be negative");
        }
        return price.setScale(2, RoundingMode.HALF_UP);
    }

}
