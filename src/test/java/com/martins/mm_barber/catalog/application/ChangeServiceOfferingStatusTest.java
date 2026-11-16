package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ChangeServiceOfferingStatusTest {

    private InMemoryServiceOfferingRepository repository;
    private CreateServiceOffering createServiceOffering;
    private ChangeServiceOfferingStatus changeStatus;

    @BeforeEach
    void setUp() {
        repository = new InMemoryServiceOfferingRepository();
        createServiceOffering = new CreateServiceOffering(repository);
        changeStatus = new ChangeServiceOfferingStatus(repository);
    }

    @Test
    void deactivatesAndReactivatesPersistingTheChange() {
        ServiceOffering created = createServiceOffering.execute(
                new CreateServiceOfferingCommand(
                        1L,
                        "Haircut",
                        null,
                        BigDecimal.TEN));

        changeStatus.execute(1L, created.getId(), false);
        assertFalse(repository.findById(created.getId()).orElseThrow().isActive());

        changeStatus.execute(1L, created.getId(), true);
        assertTrue(repository.findById(created.getId()).orElseThrow().isActive());
    }

    @Test
    void cannotChangeStatusOfAnotherBarbershopsOffering() {
        ServiceOffering created = createServiceOffering.execute(
                new CreateServiceOfferingCommand(
                        1L,
                        "Haircut",
                        null,
                        BigDecimal.TEN));

        assertThrows(ServiceOfferingNotFoundException.class,
                () -> changeStatus.execute(2L, created.getId(), false));
    }
}