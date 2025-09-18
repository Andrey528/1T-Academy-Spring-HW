package paymentServiceApp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import paymentServiceApp.dto.exception.ProductExecutorErrorResponseDto;
import paymentServiceApp.dto.exception.SimpleExceptionDto;
import paymentServiceApp.exception.EmptyRequestException;
import paymentServiceApp.exception.ExternalServiceException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ProductExecutorErrorResponseDto handleExternalServiceException(ExternalServiceException e) {
        return e.getExternalError();
    }

    @ExceptionHandler(EmptyRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleExceptionDto handleEmptyRequestException(EmptyRequestException e) {
        return new SimpleExceptionDto(e.getMessage());
    }
}
