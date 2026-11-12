package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateServiceOffering {

    private final ServiceOfferingRepository repository;

    public CreateServiceOffering(ServiceOfferingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServiceOffering execute(CreateServiceOfferingCommand command) {
        ServiceOffering offering = ServiceOffering.create(
                command.barbershopId(),
                command.name(),
                command.description(),
                command.defaultPrice());

        if (repository.existsByBarbershopIdAndName(offering.getBarbershopId(), offering.getName())) {
            throw new ServiceOfferingAlreadyExistsException(offering.getName());
        }

        return repository.save(offering);
    }
}
