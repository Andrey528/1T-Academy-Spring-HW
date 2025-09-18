package productServiceApp.exception;

import lombok.Getter;
import productServiceApp.dto.exception.PaymentExecutorErrorResponseDto;

@Getter
public class ExternalServiceException extends RuntimeException {

    private final PaymentExecutorErrorResponseDto externalError;

    public ExternalServiceException(String message, PaymentExecutorErrorResponseDto externalError) {
        super(message);
        this.externalError = externalError;
    }
}
