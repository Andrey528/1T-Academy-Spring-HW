package productServiceApp.dto.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PaymentExecutorErrorResponseDto {

    private String url;
    private String method;
    private String message;
}
