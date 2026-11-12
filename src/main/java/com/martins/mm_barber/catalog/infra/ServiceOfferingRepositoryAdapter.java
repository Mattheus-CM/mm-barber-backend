package com.martins.mm_barber.catalog.infra;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServiceOfferingRepositoryAdapter implements ServiceOfferingRepository {

    private final SpringDataServiceOfferingRepository springDataServiceOfferingRepository;
    private final ServiceOfferingPersistenceMapper mapper;

    public ServiceOfferingRepositoryAdapter(
            SpringDataServiceOfferingRepository springDataServiceOfferingRepository,
            ServiceOfferingPersistenceMapper mapper) {
        this.springDataServiceOfferingRepository = springDataServiceOfferingRepository;
        this.mapper = mapper;
    }

    @Override
    public ServiceOffering save(ServiceOffering offering) {
        ServiceOfferingJpaEntity saved = springDataServiceOfferingRepository
                .save(mapper.toEntity(offering));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ServiceOffering> findById(Long id) {
        return springDataServiceOfferingRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ServiceOffering> findAllByBarbershopId(Long barbershopId) {
        return springDataServiceOfferingRepository.findAllByBarbershopId(barbershopId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByBarbershopIdAndName(Long barbershopId, String name) {
        return springDataServiceOfferingRepository.existsByBarbershopIdAndNameIgnoreCase(barbershopId, name);
    }
}
