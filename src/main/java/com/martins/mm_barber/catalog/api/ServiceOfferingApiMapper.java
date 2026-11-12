package com.martins.mm_barber.catalog.api;

import com.martins.mm_barber.catalog.application.CreateServiceOfferingCommand;
import com.martins.mm_barber.catalog.domain.ServiceOffering;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceOfferingApiMapper {

    @Mapping(target = "barbershopId", source = "barbershopId")
    CreateServiceOfferingCommand toCommand(Long barbershopId, CreateServiceOfferingRequest request);

    ServiceOfferingResponse toResponse(ServiceOffering serviceOffering);

    List<ServiceOfferingResponse> toResponseList(List<ServiceOffering> serviceOfferings);
}
