package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class GetServiceOfferingTest {

    private CreateServiceOffering createServiceOffering;
    private GetServiceOffering getServiceOffering;

    @BeforeEach
    void setup() {
        InMemoryServiceOfferingRepository repository = new InMemoryServiceOfferingRepository();
        createServiceOffering = new CreateServiceOffering(repository);
        getServiceOffering = new GetServiceOffering(repository);
    }

    private ServiceOffering create(Long barbershopId, String name) {
        return createServiceOffering.execute(
                new CreateServiceOfferingCommand(
                        barbershopId, name, null, new BigDecimal("35.0"))
        );
    }

    @Test
    void getServiceOfferingById() {
        create(2L, "Beard trim");
        ServiceOffering created = create(1L, "Low Fade");

        ServiceOffering serviceOffering = getServiceOffering.execute(
                created.getId(), created.getBarbershopId());

        assertEquals(created.getId(), serviceOffering.getId());
        assertEquals(1L, serviceOffering.getBarbershopId());
        assertEquals("Low Fade", serviceOffering.getName());
    }

    @Test
    void cannotGetAnOfferingOfAnotherBarbershop() {
        ServiceOffering created = create(1L, "Low Fade");

        assertThrows(ServiceOfferingNotFoundException.class,
                () -> getServiceOffering.execute(created.getId(), 2L));
    }

    @Test
    void failsWhenTheServiceOfferingDoesNotExist() {
        assertThrows(ServiceOfferingNotFoundException.class,
        () -> getServiceOffering.execute(999L, 1L));
    }
}
