package com.martins.mm_barber.catalog.api;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateServiceOfferingRequest(
        @NotBlank @Size(max = 100) String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin("0.00") @Digits(integer = 8, fraction = 2) BigDecimal defaultPrice) {

}
