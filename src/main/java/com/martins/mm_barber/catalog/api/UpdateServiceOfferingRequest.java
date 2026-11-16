package com.martins.mm_barber.catalog.api;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateServiceOfferingRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin("0.00") @Digits(integer = 8, fraction = 2) BigDecimal defaultPrice) {
}
