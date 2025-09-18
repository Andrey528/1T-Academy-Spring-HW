package paymentServiceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import paymentServiceApp.model.FinancialTransaction;

import java.util.List;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

    @Query(value = "SELECT FinancialTransaction FROM FinancialTransaction ft WHERE ft.userInitiatorId = :userId OR ft.userRecipientId = :userId")
    List<FinancialTransaction> findByUserId(Long userId);

    @Query(value = "SELECT FinancialTransaction FROM FinancialTransaction ft WHERE ft.productFromId = :productId OR ft.productToId = :productId")
    List<FinancialTransaction> findByProductId(Long productId);

    @Query(value = "SELECT FinancialTransaction FROM FinancialTransaction ft WHERE (ft.userInitiatorId = :userId AND ft.productFromId = :productId)" +
            " OR (ft.userRecipientId = :userId AND ft.productToId = :productId)")
    List<FinancialTransaction> findByUserIdAndProductId(Long userId, Long productId);
}
