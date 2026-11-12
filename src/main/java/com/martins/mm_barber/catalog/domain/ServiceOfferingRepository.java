package com.martins.mm_barber.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface ServiceOfferingRepository {

    ServiceOffering save(ServiceOffering offering);

    Optional<ServiceOffering> findById(Long id);

    List<ServiceOffering> findAllByBarbershopId(Long barbershopId);

    boolean existsByBarbershopIdAndName(Long barbershopId, String name);
}
