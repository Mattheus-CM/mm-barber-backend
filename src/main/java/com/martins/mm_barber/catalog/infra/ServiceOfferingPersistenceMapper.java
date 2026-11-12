package com.martins.mm_barber.catalog.infra;

import com.martins.mm_barber.catalog.domain.ServiceOffering;
import org.springframework.stereotype.Component;

@Component
public class ServiceOfferingPersistenceMapper {

    public ServiceOffering toDomain(ServiceOfferingJpaEntity entity) {
        return ServiceOffering.reconstitute(
                entity.getId(),
                entity.getBarbershopId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDefaultPrice(),
                entity.isActive());
    }

    public ServiceOfferingJpaEntity toEntity(ServiceOffering serviceOffering) {
        ServiceOfferingJpaEntity entity = new ServiceOfferingJpaEntity();
        entity.setId(serviceOffering.getId());
        entity.setBarbershopId(serviceOffering.getBarbershopId());
        entity.setName(serviceOffering.getName());
        entity.setDescription(serviceOffering.getDescription());
        entity.setDefaultPrice(serviceOffering.getDefaultPrice());
        entity.setActive(serviceOffering.isActive());
        return entity;
    }
}
