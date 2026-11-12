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
        return saved;
    }

    @Override
    public Optional<ServiceOffering> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ServiceOffering> findAllByBarbershopId(Long barbershopId) {
        return store.values().stream()
                .filter(o -> o.getBarbershopId().equals(barbershopId))
                .toList();
    }

    @Override
    public boolean existsByBarbershopIdAndName(Long barbershopId, String name) {
        return store.values().stream()
                .anyMatch(o -> o.getBarbershopId().equals(barbershopId)
                        && o.getName().equalsIgnoreCase(name));
    }
}
