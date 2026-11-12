package com.martins.mm_barber.catalog.application;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListServiceOfferings {

    private final ServiceOfferingRepository repository;

    public ListServiceOfferings(ServiceOfferingRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ServiceOffering> execute(Long barbershopId) {
        return repository.findAllByBarbershopId(barbershopId);
    }

}
