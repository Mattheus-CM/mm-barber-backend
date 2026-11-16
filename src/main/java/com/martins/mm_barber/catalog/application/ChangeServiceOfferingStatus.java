package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeServiceOfferingStatus {

    private final ServiceOfferingRepository repository;

    public ChangeServiceOfferingStatus(ServiceOfferingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServiceOffering execute(Long serviceId, Long barbershopId, boolean active) {
        ServiceOffering serviceOffering = repository.findByIdAndBarbershopId(serviceId, barbershopId)
                .orElseThrow(() -> new ServiceOfferingNotFoundException(serviceId, barbershopId));

        if (active) {
            serviceOffering.activate();
        } else {
            serviceOffering.deactivate();
        }

        return repository.save(serviceOffering);
    }
}
