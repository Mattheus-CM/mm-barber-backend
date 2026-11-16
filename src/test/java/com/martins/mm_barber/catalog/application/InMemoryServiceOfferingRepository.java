package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryServiceOfferingRepository implements ServiceOfferingRepository {

    private final Map<Long, ServiceOffering> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public ServiceOffering save(ServiceOffering offering) {
        Long id = offering.getId() != null ? offering.getId() : sequence.incrementAndGet();
        ServiceOffering saved = ServiceOffering.reconstitute(
                id, offering.getBarbershopId(), offering.getName(),
                offering.getDescription(), offering.getDefaultPrice(), offering.isActive());
        store.put(id, saved);
        return copy(saved);
    }

    @Override
    public Optional<ServiceOffering> findById(Long id) {
        return Optional.ofNullable(store.get(id)).map(this::copy);
    }

    @Override
    public Optional<ServiceOffering> findByIdAndBarbershopId(Long id, Long barbershopId) {
        return findById(id).filter(serviceOffering ->
                serviceOffering.getBarbershopId().equals(barbershopId));
    }

    @Override
    public List<ServiceOffering> findAllByBarbershopId(Long barbershopId) {
        return store.values().stream()
                .filter(serviceOffering -> serviceOffering
                        .getBarbershopId().equals(barbershopId))
                .map(this::copy)
                .toList();
    }

    @Override
    public boolean existsByBarbershopIdAndName(Long barbershopId, String name) {
        return store.values().stream()
                .anyMatch(serviceOffering -> serviceOffering
                        .getBarbershopId().equals(barbershopId)
                        && serviceOffering.getName().equalsIgnoreCase(name));
    }

    private ServiceOffering copy(ServiceOffering serviceOffering) {
        return ServiceOffering.reconstitute(
                serviceOffering.getId(),
                serviceOffering.getBarbershopId(),
                serviceOffering.getName(),
                serviceOffering.getDescription(),
                serviceOffering.getDefaultPrice(),
                serviceOffering.isActive());
    }
}