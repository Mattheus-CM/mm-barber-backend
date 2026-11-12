package com.martins.mm_barber.catalog.application;

import java.math.BigDecimal;

public record CreateServiceOfferingCommand(Long barbershopId, String name, String description,
                                           BigDecimal defaultPrice) {
}
