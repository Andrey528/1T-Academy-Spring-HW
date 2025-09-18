package paymentServiceApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import paymentServiceApp.enums.FinancialTransactionState;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "financial_transaction")
public class FinancialTransaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_initiator_id")
    private Long userInitiatorId;

    @Column(name = "product_from_id")
    private Long productFromId;

    @Column(name = "user_recipient_id")
    private Long userRecipientId;

    @Column(name = "product_to_id")
    private Long productToId;

    @Column(name = "transfer_amount")
    private BigDecimal transferAmount;

    @Column(name = "state")
    private FinancialTransactionState state;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    public FinancialTransaction(Long userInitiatorId, Long productFromId, Long userRecipientId, Long productToId, BigDecimal transferAmount, FinancialTransactionState state) {
        this.userInitiatorId = userInitiatorId;
        this.productFromId = productFromId;
        this.userRecipientId = userRecipientId;
        this.productToId = productToId;
        this.transferAmount = transferAmount;
        this.state = state;
    }
}
