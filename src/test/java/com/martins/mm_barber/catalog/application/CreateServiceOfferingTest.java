package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreateServiceOfferingTest {

    private InMemoryServiceOfferingRepository repository;
    private CreateServiceOffering createServiceOffering;
    private ListServiceOfferings listServiceOfferings;

    @BeforeEach
    void setUp() {
        repository = new InMemoryServiceOfferingRepository();
        createServiceOffering = new CreateServiceOffering(repository);
        listServiceOfferings = new ListServiceOfferings(repository);
    }

    @Test
    void createsOfferingAndAssignsId() {
        ServiceOffering created = createServiceOffering.execute(
                new CreateServiceOfferingCommand(1L, "Low Fade", null, new BigDecimal("35")));

        assertNotNull(created.getId());
        assertEquals("Low Fade", created.getName());
    }

    @Test
    void rejectsDuplicateNameInSameBarbershop() {
        createServiceOffering.execute(
                new CreateServiceOfferingCommand(1L, "Low Fade", null, BigDecimal.TEN));

        assertThrows(ServiceOfferingAlreadyExistsException.class,
                () -> createServiceOffering.execute(
                        new CreateServiceOfferingCommand(1L, " Low Fade ", null, BigDecimal.TEN)));
    }

    @Test
    void allowsSameNameInDifferentBarbershops() {
        createServiceOffering.execute(
                new CreateServiceOfferingCommand(1L, "Low Fade", null, BigDecimal.TEN));

        assertDoesNotThrow(() -> createServiceOffering.execute(
                new CreateServiceOfferingCommand(2L, "Low Fade", null, BigDecimal.TEN)));
    }

    @Test
    void listsOnlyOfferingsOfTheGivenBarbershop() {
        createServiceOffering.execute(
                new CreateServiceOfferingCommand(1L, "Low Fade", null, BigDecimal.TEN));
        createServiceOffering.execute(
                new CreateServiceOfferingCommand(2L, "Old Money", null, BigDecimal.TEN));

        assertEquals(1, listServiceOfferings.execute(1L).size());
    }
}
