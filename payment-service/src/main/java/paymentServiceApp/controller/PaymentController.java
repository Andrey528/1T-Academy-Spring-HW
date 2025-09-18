package paymentServiceApp.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import paymentServiceApp.dto.FinancialTransactionRequest;
import paymentServiceApp.dto.PaymentRequest;
import paymentServiceApp.dto.PaymentResponse;
import paymentServiceApp.model.FinancialTransaction;
import paymentServiceApp.service.PaymentService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public List<FinancialTransaction> getFinancialTransactionHistory(
            @RequestBody FinancialTransactionRequest request
    ) {
        return paymentService.getFinancialTransactionHistory(request);
    }

    @PostMapping("/pay")
    public PaymentResponse pay(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}
