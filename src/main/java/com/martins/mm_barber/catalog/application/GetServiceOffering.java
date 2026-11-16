package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetServiceOffering {

    private final ServiceOfferingRepository repository;

    public GetServiceOffering(ServiceOfferingRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ServiceOffering execute(Long serviceId, Long barbershopId) {
        return repository.findByIdAndBarbershopId(serviceId, barbershopId)
                .orElseThrow(() -> new ServiceOfferingNotFoundException(serviceId, barbershopId));
    }
}
