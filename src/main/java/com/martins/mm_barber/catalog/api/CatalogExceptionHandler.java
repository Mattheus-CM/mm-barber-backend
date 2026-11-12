package com.martins.mm_barber.catalog.api;

import com.martins.mm_barber.catalog.application.ServiceOfferingAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CatalogExceptionHandler {

    @ExceptionHandler(value = {ServiceOfferingAlreadyExistsException.class})
    public ProblemDetail handleAlreadyExists(ServiceOfferingAlreadyExistsException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }
}
