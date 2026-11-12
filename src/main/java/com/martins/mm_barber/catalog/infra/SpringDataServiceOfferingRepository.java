package com.martins.mm_barber.catalog.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataServiceOfferingRepository
        extends JpaRepository<ServiceOfferingJpaEntity, Long> {

    List<ServiceOfferingJpaEntity> findAllByBarbershopId(Long barbershopId);

    boolean existsByBarbershopIdAndNameIgnoreCase(Long barbershopId, String name);
}
