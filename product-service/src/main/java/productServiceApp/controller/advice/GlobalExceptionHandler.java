package productServiceApp.controller.advice;

import productServiceApp.dto.exception.PaymentExecutorErrorResponseDto;
import productServiceApp.dto.exception.SimpleExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import productServiceApp.exception.EmptyRequestException;
import productServiceApp.exception.ExternalServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleExceptionDto handleEntityNotFoundException(EntityNotFoundException e) {
        return new SimpleExceptionDto(e.getMessage());
    }

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public PaymentExecutorErrorResponseDto handleExternalServiceException(ExternalServiceException e) {
        return e.getExternalError();
    }

    @ExceptionHandler(EmptyRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleExceptionDto handleEmptyRequestException(EmptyRequestException e) {
        return new SimpleExceptionDto(e.getMessage());
    }
}
