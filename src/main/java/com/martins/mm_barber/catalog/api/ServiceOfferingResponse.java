package com.martins.mm_barber.catalog.api;

import java.math.BigDecimal;

public record ServiceOfferingResponse(
        Long id,
        Long barbershopId,
        String name,
        String description,
        BigDecimal defaultPrice,
        boolean active) {
}
