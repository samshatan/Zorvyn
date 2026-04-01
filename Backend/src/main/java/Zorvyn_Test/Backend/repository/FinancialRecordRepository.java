package Zorvyn_Test.Backend.repository;

import Zorvyn_Test.Backend.model.FinancialRecord;
import Zorvyn_Test.Backend.model.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, UUID> {
    
    List<FinancialRecord> findByDeletedFalseOrderByDateDesc();

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.type = :type AND f.deleted = false")
    BigDecimal getTotalAmountByType(RecordType type);

    @Query("SELECT f.category, COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.type = 'EXPENSE' AND f.deleted = false GROUP BY f.category")
    List<Object[]> getCategoryWiseExpenses();
}
