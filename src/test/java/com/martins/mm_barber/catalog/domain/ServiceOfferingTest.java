package com.martins.mm_barber.catalog.domain;

import com.martins.mm_barber.shared.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceOfferingTest {

    @Test
    void createsActiveOfferingWithoutId() {
        ServiceOffering offering = ServiceOffering.create(1L, " Corte de Cabelo ", null, new BigDecimal("20"));

        assertNull(offering.getId());
        assertEquals("Corte de Cabelo", offering.getName());
        assertEquals(new BigDecimal("20.00"), offering.getDefaultPrice());
        assertTrue(offering.isActive());
    }

    @Test
    void rejectsBlankName() {
        assertThrows(DomainException.class, () -> ServiceOffering.create(1L, " ",
                null, new BigDecimal("20")));
    }

    @Test
    void rejectsNegativePrice() {
        assertThrows(DomainException.class, () -> ServiceOffering.create(1L,
                "Low Fade", null, new BigDecimal("-20")));
    }

    @Test
    void deactivatesAndReactivates() {
        ServiceOffering offering = ServiceOffering.create(1L, "Old Money",
                null, new BigDecimal("40"));

        offering.deactivate();
        assertFalse(offering.isActive());

        offering.activate();
        assertTrue(offering.isActive());
    }
}
