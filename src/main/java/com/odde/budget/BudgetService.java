package com.odde.budget;

import java.time.LocalDate;
import java.util.List;

public class BudgetService {
    List<Budget> budgetList;

    public BudgetService(BudgetRepo budgetRepo) {
        budgetList = budgetRepo.findAll();
    }

    public long queryBudget(LocalDate start, LocalDate end) {
        return budgetList.stream()
                .mapToLong(budget -> budget.getOverlappingAmount(new TimePeriod(start, end)))
                .sum();
    }

}
