package com.martins.mm_barber.catalog.infra;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import com.martins.mm_barber.catalog.domain.ServiceOfferingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServiceOfferingRepositoryAdapter implements ServiceOfferingRepository {

    private final SpringDataServiceOfferingRepository repository;
    private final ServiceOfferingPersistenceMapper mapper;

    public ServiceOfferingRepositoryAdapter(
            SpringDataServiceOfferingRepository repository,
            ServiceOfferingPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ServiceOffering save(ServiceOffering offering) {
        ServiceOfferingJpaEntity saved = repository.save(mapper.toEntity(offering));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ServiceOffering> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ServiceOffering> findAllByBarbershopId(Long barbershopId) {
        return repository.findAllByBarbershopId(barbershopId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByBarbershopIdAndName(Long barbershopId, String name) {
        return repository.existsByBarbershopIdAndNameIgnoreCase(barbershopId, name);
    }

    @Override
    public Optional<ServiceOffering> findByIdAndBarbershopId(Long id, Long barbershopId) {
        return repository.findByIdAndBarbershopId(id, barbershopId).map(mapper::toDomain);
    }

}
