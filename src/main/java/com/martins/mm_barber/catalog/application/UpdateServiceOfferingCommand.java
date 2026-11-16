package com.martins.mm_barber.catalog.application;

import java.math.BigDecimal;

public record UpdateServiceOfferingCommand(
        Long barbershopId,
        Long serviceId,
        String name,
        String description,
        BigDecimal defaultPrice) {
}
