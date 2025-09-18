package paymentServiceApp.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import paymentServiceApp.enums.FinancialTransactionState;
import paymentServiceApp.dto.*;
import paymentServiceApp.exception.EmptyRequestException;
import paymentServiceApp.model.FinancialTransaction;
import paymentServiceApp.repository.FinancialTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PaymentService {

    private final FinancialTransactionRepository repository;

    private final ProductIntegrationService productIntegrationService;

    public List<FinancialTransaction> getFinancialTransactionHistory(FinancialTransactionRequest request) {

        if (request == null)
            throw new EmptyRequestException("Empty get financial transaction request");

        Long userId = request.getUserId();
        Long productId = request.getProductId();

        if (userId == null && productId == null)
            throw new EmptyRequestException("Can't identify transaction because userId and productId is null");

        List<FinancialTransaction> financialTransactions;

        if (userId != null && productId != null) {
            financialTransactions = repository.findByUserIdAndProductId(userId, productId);
        } else if (userId != null) {
            financialTransactions = repository.findByUserId(userId);
        } else {
            financialTransactions = repository.findByProductId(productId);
        }

        return financialTransactions;
    }

    public PaymentResponse processPayment(PaymentRequest request) {

        Long initiatorProductId = request.getProductInitiatorId();
        Long recipientProductId = request.getProductRecipientId();
        BigDecimal amount = request.getAmount();

        if (initiatorProductId == null || recipientProductId == null
                || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            repository.save(new FinancialTransaction(
                    null,
                    initiatorProductId,
                    null,
                    recipientProductId,
                    amount,
                    FinancialTransactionState.INVALID_PARAMETERS));

            return new PaymentResponse(FinancialTransactionState.INVALID_PARAMETERS, "Некорректные параметры", LocalDateTime.now());
        }

        GetProductsResponse response = productIntegrationService.getProductsInfo(
                new GetProductsRequest(List.of(request.getProductInitiatorId(), request.getProductRecipientId())));


        Optional<ProductDto> initiatorProductOpt = response.getProducts().stream()
                .filter(p -> p.getProductId().equals(initiatorProductId))
                .findFirst();

        Optional<ProductDto> recipientProductOpt = response.getProducts().stream()
                .filter(p -> p.getProductId().equals(recipientProductId))
                .findFirst();

        if (!initiatorProductOpt.isPresent()) {
            repository.save(new FinancialTransaction(
                    null,
                    initiatorProductId,
                    null,
                    recipientProductId,
                    amount,
                    FinancialTransactionState.PRODUCT_NOT_FOUND));

            return new PaymentResponse(FinancialTransactionState.PRODUCT_NOT_FOUND, "Исходный продукт не найден", LocalDateTime.now());
        }

        ProductDto initiatorProduct = initiatorProductOpt.get();

        if (!recipientProductOpt.isPresent()) {
            repository.save(new FinancialTransaction(
                    initiatorProduct.getUserId(),
                    initiatorProductId,
                    null,
                    recipientProductId,
                    amount,
                    FinancialTransactionState.PRODUCT_NOT_FOUND));

            return new PaymentResponse(FinancialTransactionState.PRODUCT_NOT_FOUND, "Целевой продукт не найден", LocalDateTime.now());
        }

        ProductDto recipientProduct = recipientProductOpt.get();

        if (initiatorProduct.getBalance().compareTo(amount) < 0) {
            repository.save(new FinancialTransaction(
                    initiatorProduct.getUserId(),
                    initiatorProductId,
                    recipientProduct.getUserId(),
                    recipientProductId,
                    amount,
                    FinancialTransactionState.PRODUCT_NOT_FOUND));

            return new PaymentResponse(FinancialTransactionState.INSUFFICIENT_FUNDS, "Недостаточно средств на исходном продукте", LocalDateTime.now());
        }

        initiatorProduct.setBalance(initiatorProduct.getBalance().subtract(amount));
        recipientProduct.setBalance(recipientProduct.getBalance().add(amount));

        try {
            productIntegrationService.updateProductsBalance(
                    new UpdateProductsRequest(List.of(initiatorProduct, recipientProduct))
            );

            repository.save(new FinancialTransaction(
                    initiatorProduct.getUserId(),
                    initiatorProductId,
                    recipientProduct.getUserId(),
                    recipientProductId,
                    amount,
                    FinancialTransactionState.SUCCESS));

            return new PaymentResponse(FinancialTransactionState.SUCCESS, "Перевод выполнен успешно", LocalDateTime.now());
        } catch (Exception e) {
            repository.save(new FinancialTransaction(
                    initiatorProduct.getUserId(),
                    initiatorProductId,
                    recipientProduct.getUserId(),
                    recipientProductId,
                    amount,
                    FinancialTransactionState.ERROR));

            return new PaymentResponse(FinancialTransactionState.ERROR, "Ошибка при обновлении балансов", LocalDateTime.now());
        }
    }
}
