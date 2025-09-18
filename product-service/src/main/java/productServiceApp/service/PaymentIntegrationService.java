package productServiceApp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import productServiceApp.dto.PaymentRequest;
import productServiceApp.dto.PaymentResponse;

@AllArgsConstructor
@Service
public class PaymentIntegrationService {

    private final RestTemplate restTemplate;

    public PaymentResponse initiatePaymentProcess(PaymentRequest request) {
        return restTemplate.postForObject(
                "/api/v1/payments/pay",
                request,
                PaymentResponse.class);
    }
}
