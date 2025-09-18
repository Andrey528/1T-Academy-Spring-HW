package productServiceApp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import productServiceApp.dto.PaymentRequest;
import productServiceApp.dto.PaymentResponse;
import productServiceApp.service.PaymentIntegrationService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/transfer")
public class PaymentIntegrationController {

    private final PaymentIntegrationService paymentIntegrationService;

    @PostMapping("/pay")
    public PaymentResponse initiatePaymentProcess(
            @RequestBody PaymentRequest request) {
        return paymentIntegrationService.initiatePaymentProcess(request);
    }
}
