package paymentServiceApp.exception;

import lombok.Getter;
import paymentServiceApp.dto.exception.ProductExecutorErrorResponseDto;

@Getter
public class ExternalServiceException extends RuntimeException {

    private final ProductExecutorErrorResponseDto externalError;

    public ExternalServiceException(String message, ProductExecutorErrorResponseDto externalError) {
        super(message);
        this.externalError = externalError;
    }
}
