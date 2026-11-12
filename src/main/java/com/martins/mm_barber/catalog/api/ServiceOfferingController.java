package com.martins.mm_barber.catalog.api;

import com.martins.mm_barber.catalog.application.CreateServiceOffering;
import com.martins.mm_barber.catalog.application.ListServiceOfferings;
import com.martins.mm_barber.catalog.domain.ServiceOffering;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barbershops/{barbershopId}/services")
public class ServiceOfferingController {

    private final CreateServiceOffering createServiceOffering;
    private final ListServiceOfferings listServiceOfferings;
    private final ServiceOfferingApiMapper serviceOfferingApiMapper;

    public ServiceOfferingController(CreateServiceOffering createServiceOffering,
                                     ListServiceOfferings listServiceOfferings,
                                     ServiceOfferingApiMapper serviceOfferingApiMapper) {
        this.createServiceOffering = createServiceOffering;
        this.listServiceOfferings = listServiceOfferings;
        this.serviceOfferingApiMapper = serviceOfferingApiMapper;
    }

    @PostMapping
    public ResponseEntity<ServiceOfferingResponse> create(
            @PathVariable Long barbershopId,
            @Valid @RequestBody CreateServiceOfferingRequest createServiceOfferingRequest) {
        ServiceOffering created = createServiceOffering.execute(
                serviceOfferingApiMapper.toCommand(barbershopId, createServiceOfferingRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceOfferingApiMapper.toResponse(created));
    }

    @GetMapping
    public List<ServiceOfferingResponse> list(@PathVariable Long barbershopId) {
        return serviceOfferingApiMapper.toResponseList(listServiceOfferings.execute(barbershopId));
    }

}
