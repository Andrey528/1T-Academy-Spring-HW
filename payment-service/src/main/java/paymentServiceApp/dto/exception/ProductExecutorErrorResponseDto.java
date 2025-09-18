package paymentServiceApp.dto.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductExecutorErrorResponseDto {

    private String url;
    private String method;
    private String message;
}
