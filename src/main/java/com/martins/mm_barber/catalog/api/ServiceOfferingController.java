package com.martins.mm_barber.catalog.api;

import com.martins.mm_barber.catalog.application.*;
import com.martins.mm_barber.catalog.domain.ServiceOffering;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/barbershops/{barbershopId}/services")
public class ServiceOfferingController {

    private final CreateServiceOffering createServiceOffering;
    private final ListServiceOfferings listServiceOfferings;
    private final GetServiceOffering getServiceOffering;
    private final UpdateServiceOffering updateServiceOffering;
    private final ChangeServiceOfferingStatus changeServiceOfferingStatus;
    private final ServiceOfferingApiMapper mapper;

    public ServiceOfferingController(CreateServiceOffering createServiceOffering,
                                     ListServiceOfferings listServiceOfferings,
                                     GetServiceOffering getServiceOffering,
                                     UpdateServiceOffering updateServiceOffering,
                                     ChangeServiceOfferingStatus changeServiceOfferingStatus,
                                     ServiceOfferingApiMapper mapper) {
        this.createServiceOffering = createServiceOffering;
        this.listServiceOfferings = listServiceOfferings;
        this.getServiceOffering = getServiceOffering;
        this.updateServiceOffering = updateServiceOffering;
        this.changeServiceOfferingStatus = changeServiceOfferingStatus;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ServiceOfferingResponse> create(
            @PathVariable Long barbershopId,
            @Valid @RequestBody CreateServiceOfferingRequest request) {
        ServiceOffering created = createServiceOffering
                .execute(mapper.toCommand(barbershopId, request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{serviceId}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @GetMapping
    public List<ServiceOfferingResponse> list(
            @PathVariable Long barbershopId,
            @RequestParam(defaultValue = "false") boolean onlyActive) {
        return mapper.toResponseList(listServiceOfferings.execute(barbershopId, onlyActive));
    }

    @GetMapping("/{serviceId}")
    public ServiceOfferingResponse get(
            @PathVariable Long serviceId, @PathVariable Long barbershopId) {
        return mapper.toResponse(getServiceOffering.execute(serviceId, barbershopId));
    }

    @PutMapping("/{serviceId}")
    public ServiceOfferingResponse update(
            @PathVariable Long serviceId,
            @PathVariable Long barbershopId,
            @Valid @RequestBody UpdateServiceOfferingRequest request) {
        return mapper.toResponse(
                updateServiceOffering.execute(mapper.toCommand(serviceId, barbershopId, request)));
    }

    @PatchMapping("/{serviceId}/activate")
    public ServiceOfferingResponse activate(
            @PathVariable Long serviceId, @PathVariable Long barbershopId) {
        return mapper.toResponse(
                changeServiceOfferingStatus.execute(serviceId, barbershopId, true));
    }

    @PatchMapping("/{serviceId}/deactivate")
    public ServiceOfferingResponse deactivate(
            @PathVariable Long serviceId, @PathVariable Long barbershopId) {
        return mapper.toResponse(changeServiceOfferingStatus.execute(serviceId, barbershopId, false));
    }

}
