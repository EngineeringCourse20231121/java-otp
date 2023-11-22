package com.odde.budget;

import java.time.LocalDate;
import java.time.Period;

public class BudgetService {

    private final BudgetRepo budgetRepo;

    public BudgetService(BudgetRepo budgetRepo) {
        this.budgetRepo = budgetRepo;
    }

    public long queryBudget(LocalDate start, LocalDate end) {
        if (budgetRepo.findAll().isEmpty()) {
            return 0;
        }
        return Period.between(start, end).getDays() + 1;
    }
}
