package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateServiceOffering {

    private final ServiceOfferingRepository repository;

    public UpdateServiceOffering(ServiceOfferingRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ServiceOffering execute(UpdateServiceOfferingCommand command) {
        ServiceOffering serviceOffering = repository.findByIdAndBarbershopId(
                command.serviceId(),
                command.barbershopId())
                .orElseThrow(() -> new ServiceOfferingNotFoundException(
                        command.serviceId(),
                        command.barbershopId()
                ));

        String previousName = serviceOffering.getName();
        serviceOffering.update(
                command.name(),
                command.description(),
                command.defaultPrice());

        boolean renamed = !previousName.equalsIgnoreCase(serviceOffering.getName());
        if (renamed && repository.existsByBarbershopIdAndName(
                serviceOffering.getBarbershopId(),
                serviceOffering.getName()
        )) {
            throw new ServiceOfferingAlreadyExistsException(serviceOffering.getName());
        }

        return repository.save(serviceOffering);
    }
}
