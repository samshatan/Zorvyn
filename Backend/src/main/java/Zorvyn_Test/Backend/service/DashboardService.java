package Zorvyn_Test.Backend.service;

import Zorvyn_Test.Backend.dto.DashboardSummaryDto;
import Zorvyn_Test.Backend.model.RecordType;
import Zorvyn_Test.Backend.repository.FinancialRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final FinancialRecordRepository recordRepository;

    public DashboardService(FinancialRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public DashboardSummaryDto getSummary() {
        BigDecimal totalIncome = recordRepository.getTotalAmountByType(RecordType.INCOME);
        BigDecimal totalExpenses = recordRepository.getTotalAmountByType(RecordType.EXPENSE);
        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        List<Object[]> categoryData = recordRepository.getCategoryWiseExpenses();
        Map<String, BigDecimal> expensesByCategory = new HashMap<>();
        for (Object[] row : categoryData) {
            expensesByCategory.put((String) row[0], (BigDecimal) row[1]);
        }

        return DashboardSummaryDto.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .expensesByCategory(expensesByCategory)
                .build();
    }
}
