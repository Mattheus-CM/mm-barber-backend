package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateServiceOfferingTest {

    private CreateServiceOffering createServiceOffering;
    private UpdateServiceOffering updateServiceOffering;

    @BeforeEach
    void setup() {
        InMemoryServiceOfferingRepository repository = new InMemoryServiceOfferingRepository();
        createServiceOffering = new CreateServiceOffering(repository);
        updateServiceOffering = new UpdateServiceOffering(repository);
    }

    private ServiceOffering create(Long barbershopId, String name) {
        return createServiceOffering.execute(
                new CreateServiceOfferingCommand(
                        barbershopId, name, null, new BigDecimal("35.0"))
        );
    }

    @Test
    void updatesNameDescriptionAndPrice() {
        ServiceOffering created = create(1L, "Loo Fade");

        ServiceOffering updated = updateServiceOffering.execute(
                new UpdateServiceOfferingCommand(
                        1L,
                        created.getId(),
                        "Low Fade",
                        "Degradê baixo",
                        new BigDecimal("20.0")
                )
        );

        assertEquals("Low Fade", updated.getName());
        assertEquals("Degradê baixo", updated.getDescription());
        assertEquals(new BigDecimal("20.00"), updated.getDefaultPrice());
    }

    @Test
    void rejectsRenamingToAnExistingName() {
        create(1L, "Haircut");
        ServiceOffering beard = create(1L, "Beard trim");

        assertThrows(ServiceOfferingAlreadyExistsException.class,
                () -> updateServiceOffering.execute(new UpdateServiceOfferingCommand(
                        1L, beard.getId(), "haircut", null, BigDecimal.TEN)));
    }

    @Test
    void allowsChangingOnlyTheCaseOfTheOwnName() {
        ServiceOffering created = create(1L, "Haircut");

        assertDoesNotThrow(() -> updateServiceOffering.execute(new UpdateServiceOfferingCommand(
                1L, created.getId(), "HAIRCUT", null, BigDecimal.TEN)));
    }

    @Test
    void cannotUpdateAnOfferingOfAnotherBarbershop() {
        ServiceOffering created = create(1L, "Haircut");

        assertThrows(ServiceOfferingNotFoundException.class,
                () -> updateServiceOffering.execute(new UpdateServiceOfferingCommand(
                        2L, created.getId(), "Haircut", null, BigDecimal.TEN)));
    }

}
